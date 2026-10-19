package com.CaioRian.AvaliacoesFisicas.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.CaioRian.AvaliacoesFisicas.models.entities.Circunferencias;
import com.CaioRian.AvaliacoesFisicas.models.entities.DobrasCutaneas;
import com.CaioRian.AvaliacoesFisicas.models.entities.User;
import com.CaioRian.AvaliacoesFisicas.repository.CircunferenciasRepository;
import com.CaioRian.AvaliacoesFisicas.repository.DobrasCutaneasRepository;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private static final String TEMPLATE_RELATORIO = "relatorio-aluno";
    private static final String LOGO_PATH = "static/images/logo.png";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final TemplateEngine templateEngine;
    private final UserService userService;
    private final CircunferenciasRepository circunferenciasRepository;
    private final DobrasCutaneasRepository dobrasCutaneasRepository;

    public RelatorioPdf gerarRelatorioPdf(UUID alunoId, LocalDate data) {

        User aluno = this.userService.findEntityById(alunoId);

        List<Circunferencias> circunferencias =
                this.circunferenciasRepository.findByAlunoIdAndData(alunoId, data);

        List<DobrasCutaneas> dobras =
                this.dobrasCutaneasRepository.findByAlunoIdAndData(alunoId, data);

        String html = renderizarHtml(aluno, data, circunferencias, dobras);

        return new RelatorioPdf(converterHtmlParaPdf(html), aluno.getNome());
    }

    private String renderizarHtml(
            User aluno,
            LocalDate data,
            List<Circunferencias> circunferencias,
            List<DobrasCutaneas> dobras
    ) {
        Context context = new Context(new Locale("pt", "BR"));
        context.setVariable("aluno", aluno);
        context.setVariable("data", data);
        context.setVariable("dataFormatada", data.format(DATE_FORMAT));
        context.setVariable("circunferencias", circunferencias);
        context.setVariable("dobras", dobras);
        context.setVariable("logoDataUri", carregarLogoComoDataUri());

        return this.templateEngine.process(TEMPLATE_RELATORIO, context);
    }

    private String carregarLogoComoDataUri() {
        try (InputStream inputStream = new ClassPathResource(LOGO_PATH).getInputStream()) {
            byte[] bytes = inputStream.readAllBytes();
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            return null;
        }
    }

    private byte[] converterHtmlParaPdf(String html) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao gerar o relatório em PDF.", e);
        }
    }

    public record RelatorioPdf(byte[] conteudo, String nomeAluno) {
    }
}
