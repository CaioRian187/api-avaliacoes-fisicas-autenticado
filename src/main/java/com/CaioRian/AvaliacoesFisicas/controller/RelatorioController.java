package com.CaioRian.AvaliacoesFisicas.controller;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CaioRian.AvaliacoesFisicas.services.RelatorioService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Relatórios")
@CrossOrigin("*")
@RestController
@RequestMapping("/api/relatorios")
@Validated
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/aluno/{alunoId}/pdf")
    public ResponseEntity<byte[]> gerarRelatorioPdf(
            @PathVariable UUID alunoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {

        RelatorioService.RelatorioPdf relatorio = this.relatorioService.gerarRelatorioPdf(alunoId, data);

        String filename = "Relatorio_" + sanitizarNomeArquivo(relatorio.nomeAluno()) + "_" + data + ".pdf";

        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename(filename, StandardCharsets.UTF_8)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(contentDisposition);
        headers.set(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.set(HttpHeaders.PRAGMA, "no-cache");
        headers.setExpires(0);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(relatorio.conteudo());
    }

    @GetMapping("/aluno/{alunoId}/pdf-comparativo")
    public ResponseEntity<byte[]> gerarRelatorioComparativoPdf(
            @PathVariable UUID alunoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data1,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data2
    ) {

        RelatorioService.RelatorioPdf relatorio =
                this.relatorioService.gerarRelatorioComparativoPdf(alunoId, data1, data2);

        String filename = "Relatorio_Comparativo_" + sanitizarNomeArquivo(relatorio.nomeAluno())
                + "_" + data1 + "_a_" + data2 + ".pdf";

        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename(filename, StandardCharsets.UTF_8)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(contentDisposition);
        headers.set(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.set(HttpHeaders.PRAGMA, "no-cache");
        headers.setExpires(0);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(relatorio.conteudo());
    }

    private String sanitizarNomeArquivo(String nome) {
        if (nome == null || nome.isBlank()) {
            return "aluno";
        }

        String sanitizado = Normalizer.normalize(nome, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[\\\\/:*?\"<>|\\p{Cntrl}]", "")
                .replaceAll("\\s+", "_")
                .replaceAll("_+", "_")
                .replaceAll("^[._]+|[._]+$", "");

        if (sanitizado.isBlank()) {
            return "aluno";
        }

        return sanitizado.length() > 80 ? sanitizado.substring(0, 80) : sanitizado;
    }
}
