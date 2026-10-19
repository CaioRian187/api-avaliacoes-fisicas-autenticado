package com.CaioRian.AvaliacoesFisicas.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.CaioRian.AvaliacoesFisicas.exceptions.RegistrosNaoEncontradosException;
import com.CaioRian.AvaliacoesFisicas.models.dto.MetricaComparativaDto;
import com.CaioRian.AvaliacoesFisicas.models.dto.RelatorioComparativoDto;
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
    private static final String TEMPLATE_COMPARATIVO = "relatorio-comparativo";
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

    public RelatorioPdf gerarRelatorioComparativoPdf(UUID alunoId, LocalDate data1, LocalDate data2) {

        User aluno = this.userService.findEntityById(alunoId);

        LocalDate dataInicial = data1.isAfter(data2) ? data2 : data1;
        LocalDate dataFinal = data1.isAfter(data2) ? data1 : data2;

        Circunferencias circunferenciasInicial =
                this.circunferenciasRepository.findFirstByAluno_IdAndDataOrderByIdAsc(alunoId, dataInicial)
                        .orElseThrow(() -> new RegistrosNaoEncontradosException(
                                "Avaliação de circunferências não encontrada para o aluno na data "
                                        + dataInicial.format(DATE_FORMAT) + "."));

        Circunferencias circunferenciasFinal =
                this.circunferenciasRepository.findFirstByAluno_IdAndDataOrderByIdAsc(alunoId, dataFinal)
                        .orElseThrow(() -> new RegistrosNaoEncontradosException(
                                "Avaliação de circunferências não encontrada para o aluno na data "
                                        + dataFinal.format(DATE_FORMAT) + "."));

        DobrasCutaneas dobrasInicial =
                this.dobrasCutaneasRepository.findFirstByAluno_IdAndDataOrderByIdAsc(alunoId, dataInicial)
                        .orElseThrow(() -> new RegistrosNaoEncontradosException(
                                "Avaliação de dobras cutâneas não encontrada para o aluno na data "
                                        + dataInicial.format(DATE_FORMAT) + "."));

        DobrasCutaneas dobrasFinal =
                this.dobrasCutaneasRepository.findFirstByAluno_IdAndDataOrderByIdAsc(alunoId, dataFinal)
                        .orElseThrow(() -> new RegistrosNaoEncontradosException(
                                "Avaliação de dobras cutâneas não encontrada para o aluno na data "
                                        + dataFinal.format(DATE_FORMAT) + "."));

        RelatorioComparativoDto relatorio = new RelatorioComparativoDto(
                aluno,
                dataInicial,
                dataFinal,
                dataInicial.format(DATE_FORMAT),
                dataFinal.format(DATE_FORMAT),
                compararCircunferencias(circunferenciasInicial, circunferenciasFinal),
                compararDobras(dobrasInicial, dobrasFinal)
        );

        String html = renderizarHtmlComparativo(relatorio);

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

    private String renderizarHtmlComparativo(RelatorioComparativoDto relatorio) {
        Context context = new Context(new Locale("pt", "BR"));
        context.setVariable("relatorio", relatorio);
        context.setVariable("aluno", relatorio.aluno());
        context.setVariable("dataInicialFormatada", relatorio.dataInicialFormatada());
        context.setVariable("dataFinalFormatada", relatorio.dataFinalFormatada());
        context.setVariable("circunferencias", relatorio.circunferencias());
        context.setVariable("dobras", relatorio.dobras());
        context.setVariable("logoDataUri", carregarLogoComoDataUri());

        return this.templateEngine.process(TEMPLATE_COMPARATIVO, context);
    }

    private List<MetricaComparativaDto> compararCircunferencias(Circunferencias inicial, Circunferencias avaliacaoFinal) {
        List<MetricaComparativaDto> metricas = new ArrayList<>();

        adicionarMetrica(metricas, "Altura", inicial.getAltura(), avaliacaoFinal.getAltura(), "cm");
        adicionarMetrica(metricas, "Peso", inicial.getPeso(), avaliacaoFinal.getPeso(), "kg");
        adicionarMetrica(metricas, "IMC", inicial.getImc(), avaliacaoFinal.getImc(), "");
        adicionarMetrica(metricas, "Ombro", inicial.getOmbro(), avaliacaoFinal.getOmbro(), "cm");
        adicionarMetrica(metricas, "Cintura", inicial.getCintura(), avaliacaoFinal.getCintura(), "cm");
        adicionarMetrica(metricas, "Quadril", inicial.getQuadril(), avaliacaoFinal.getQuadril(), "cm");
        adicionarMetrica(metricas, "Peitoral", inicial.getPeitoral(), avaliacaoFinal.getPeitoral(), "cm");
        adicionarMetrica(metricas, "Abdômen", inicial.getAbdommen(), avaliacaoFinal.getAbdommen(), "cm");
        adicionarMetrica(metricas, "Coxa Proximal Esquerda",
                inicial.getCoxaProximalEsquerda(), avaliacaoFinal.getCoxaProximalEsquerda(), "cm");
        adicionarMetrica(metricas, "Coxa Proximal Direita",
                inicial.getCoxaProximalDireita(), avaliacaoFinal.getCoxaProximalDireita(), "cm");
        adicionarMetrica(metricas, "Coxa Medial Esquerda",
                inicial.getCoxaMedialEsquerda(), avaliacaoFinal.getCoxaMedialEsquerda(), "cm");
        adicionarMetrica(metricas, "Coxa Medial Direita",
                inicial.getCoxaMedialDireita(), avaliacaoFinal.getCoxaMedialDireita(), "cm");
        adicionarMetrica(metricas, "Coxa Distal Esquerda",
                inicial.getCoxaDistalEsquerda(), avaliacaoFinal.getCoxaDistalEsquerda(), "cm");
        adicionarMetrica(metricas, "Coxa Distal Direita",
                inicial.getCoxaDistalDireita(), avaliacaoFinal.getCoxaDistalDireita(), "cm");
        adicionarMetrica(metricas, "Panturrilha Esquerda",
                inicial.getPanturrilhaEsquerda(), avaliacaoFinal.getPanturrilhaEsquerda(), "cm");
        adicionarMetrica(metricas, "Panturrilha Direita",
                inicial.getPanturrilhaDireita(), avaliacaoFinal.getPanturrilhaDireita(), "cm");
        adicionarMetrica(metricas, "Braço Relaxado Esquerdo",
                inicial.getBracoRelaxadoEsquerdo(), avaliacaoFinal.getBracoRelaxadoEsquerdo(), "cm");
        adicionarMetrica(metricas, "Braço Relaxado Direito",
                inicial.getBracoRelaxadoDireito(), avaliacaoFinal.getBracoRelaxadoDireito(), "cm");
        adicionarMetrica(metricas, "Braço Contraído Esquerdo",
                inicial.getBracoContraidoEsquerdo(), avaliacaoFinal.getBracoContraidoEsquerdo(), "cm");
        adicionarMetrica(metricas, "Braço Contraído Direito",
                inicial.getBracoContraidoDireito(), avaliacaoFinal.getBracoContraidoDireito(), "cm");
        adicionarMetrica(metricas, "Antebraço Esquerdo",
                inicial.getAntebraçoEsquerdo(), avaliacaoFinal.getAntebraçoEsquerdo(), "cm");
        adicionarMetrica(metricas, "Antebraço Direito",
                inicial.getAntebraçoDireito(), avaliacaoFinal.getAntebraçoDireito(), "cm");

        return metricas;
    }

    private List<MetricaComparativaDto> compararDobras(DobrasCutaneas inicial, DobrasCutaneas avaliacaoFinal) {
        List<MetricaComparativaDto> metricas = new ArrayList<>();

        adicionarMetrica(metricas, "Bíceps", inicial.getBiceps(), avaliacaoFinal.getBiceps(), "mm");
        adicionarMetrica(metricas, "Peitoral", inicial.getPeitoral(), avaliacaoFinal.getPeitoral(), "mm");
        adicionarMetrica(metricas, "Tríceps", inicial.getTriceps(), avaliacaoFinal.getTriceps(), "mm");
        adicionarMetrica(metricas, "Subescapular", inicial.getSubescapular(), avaliacaoFinal.getSubescapular(), "mm");
        adicionarMetrica(metricas, "Panturrilha Medial",
                inicial.getPanturrilhaMedial(), avaliacaoFinal.getPanturrilhaMedial(), "mm");
        adicionarMetrica(metricas, "Abdominal", inicial.getAbdominal(), avaliacaoFinal.getAbdominal(), "mm");
        adicionarMetrica(metricas, "Supra-ilíaca", inicial.getSuprailiaca(), avaliacaoFinal.getSuprailiaca(), "mm");
        adicionarMetrica(metricas, "Coxa", inicial.getCoxa(), avaliacaoFinal.getCoxa(), "mm");
        adicionarMetrica(metricas, "Relação Cintura/Quadril",
                inicial.getRelacaoCinturaQuadril(), avaliacaoFinal.getRelacaoCinturaQuadril(), "");
        adicionarMetrica(metricas, "Percentual de Gordura",
                inicial.getPercentualGordura(), avaliacaoFinal.getPercentualGordura(), "%");

        return metricas;
    }

    private void adicionarMetrica(
            List<MetricaComparativaDto> metricas,
            String nome,
            Double valorInicial,
            Double valorFinal,
            String unidade
    ) {
        metricas.add(new MetricaComparativaDto(
                nome,
                formatarValor(valorInicial, unidade),
                formatarValor(valorFinal, unidade),
                formatarEvolucao(valorInicial, valorFinal, unidade),
                identificarIndicador(valorInicial, valorFinal)
        ));
    }

    private String formatarValor(Double valor, String unidade) {
        if (valor == null) {
            return "-";
        }

        String numero = String.format(Locale.US, "%.1f", valor);
        return unidade == null || unidade.isBlank() ? numero : numero + " " + unidade;
    }

    private String formatarEvolucao(Double valorInicial, Double valorFinal, String unidade) {
        if (valorInicial == null || valorFinal == null) {
            return "-";
        }

        double diferenca = arredondar(valorFinal - valorInicial);
        String sufixo = unidade == null || unidade.isBlank() ? "" : " " + unidade;

        if (Math.abs(diferenca) < 0.05) {
            return "0.0" + sufixo;
        }

        return String.format(Locale.US, "%+.1f", diferenca) + sufixo;
    }

    private String identificarIndicador(Double valorInicial, Double valorFinal) {
        if (valorInicial == null || valorFinal == null) {
            return "neutro";
        }

        double diferenca = arredondar(valorFinal - valorInicial);

        if (diferenca > 0.05) {
            return "aumento";
        }

        if (diferenca < -0.05) {
            return "reducao";
        }

        return "estavel";
    }

    private double arredondar(double valor) {
        return Math.round(valor * 10.0) / 10.0;
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
