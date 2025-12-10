package br.com.curriculo.controller;


import br.com.curriculo.entity.Curriculo;
import br.com.curriculo.entity.CurriculoRequest;
import br.com.curriculo.entity.Experiencia;
import br.com.curriculo.entity.Formacao;
import br.com.curriculo.service.CurriculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CurriculoController {


    private final CurriculoService curriculoService;

    @PostMapping(value = "/gerar-cv", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "endpoint responsável pela geração de curriculos.")
    @ApiResponse(responseCode = "200", description = " success", content = {
            @Content(mediaType = "application.json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<byte[]> generateCv(@RequestBody Curriculo curriculo) {
        try {
            // Validar dados básicos
            if (curriculo.getNome() == null || curriculo.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Nome é obrigatório".getBytes());
            }

            // Gerar PDF
            byte[] pdfBytes = curriculoService.gerarPdf(curriculo);
            // Configurar headers para download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment",
                    "curriculo_" + curriculo.getNome().replace(" ", "_") + ".pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Erro ao gerar PDF: " + e.getMessage()).getBytes());
        }
    }

    // Endpoint simples para testar se API está funcionando
    @GetMapping("/teste")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "endpoint responsável para testar o funcionamento da api.")
    @ApiResponse(responseCode = "200", description = " success", content = {
            @Content(mediaType = "application.json", schema = @Schema(implementation = ResponseEntity.class))
    })
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API de Gerador de CV está funcionando!");
    }



}
