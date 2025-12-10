package br.com.curriculo.service;

import br.com.curriculo.entity.Curriculo;
import br.com.curriculo.entity.CurriculoRequest;
import br.com.curriculo.entity.Experiencia;
import br.com.curriculo.entity.Formacao;
import org.springframework.stereotype.Service;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import com.lowagie.text.Font;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@Service
public class CurriculoService {

    // Cores profissionais para o currículo
    private static final Color COR_PRIMARIA = new Color(44, 62, 80); // Azul escuro
    private static final Color COR_SECUNDARIA = new Color(52, 152, 219); // Azul
    private static final Color COR_TEXTO = new Color(51, 51, 51); // Cinza escuro

    public byte[] gerarPdf(Curriculo cv) throws DocumentException, IOException {
        // Configurar documento A4
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        // Fonte principal
        Font fonteTitulo = new Font(Font.HELVETICA, 20, Font.BOLD, COR_PRIMARIA);
        Font fonteSubtitulo = new Font(Font.HELVETICA, 14, Font.BOLD, COR_SECUNDARIA);
        Font fonteNormal = new Font(Font.HELVETICA, 11, Font.NORMAL, COR_TEXTO);
        Font fonteNormalNegrito = new Font(Font.HELVETICA, 11, Font.BOLD, COR_TEXTO);
        Font fonteCabecalho = new Font(Font.HELVETICA, 16, Font.BOLD, Color.WHITE);

        // 1. CABEÇALHO COM NOME E CARGO
        PdfPTable cabecalho = new PdfPTable(1);
        cabecalho.setWidthPercentage(100);

        PdfPCell celulaCabecalho = new PdfPCell();
        celulaCabecalho.setBackgroundColor(COR_PRIMARIA);
        celulaCabecalho.setBorder(0);
        celulaCabecalho.setPadding(20);

        Paragraph nome = new Paragraph(cv.getNome(), fonteCabecalho);
        nome.setAlignment(Element.ALIGN_CENTER);
        celulaCabecalho.addElement(nome);

        if (cv.getCargoDesejado() != null && !cv.getCargoDesejado().isEmpty()) {
            Paragraph cargo = new Paragraph(cv.getCargoDesejado(),
                    new Font(Font.HELVETICA, 14, Font.NORMAL, new Color(200, 200, 200)));
            cargo.setAlignment(Element.ALIGN_CENTER);
            celulaCabecalho.addElement(cargo);
        }

        cabecalho.addCell(celulaCabecalho);
        document.add(cabecalho);

        // 2. INFORMAÇÕES DE CONTATO
        document.add(Chunk.NEWLINE);

        PdfPTable contato = new PdfPTable(3);
        contato.setWidthPercentage(100);
        contato.setSpacingBefore(10f);

        adicionarCelulaContato(contato, "Email: " + cv.getEmail(), fonteNormal);
        adicionarCelulaContato(contato, "Telefone: " + cv.getTelefone(), fonteNormal);
        adicionarCelulaContato(contato, "Cargo Desejado: " +
                (cv.getCargoDesejado() != null ? cv.getCargoDesejado() : ""), fonteNormal);

        document.add(contato);

        // 3. RESUMO PROFISSIONAL (se existir)
        if (cv.getResumoProfissional() != null && !cv.getResumoProfissional().isEmpty()) {
            document.add(Chunk.NEWLINE);
            adicionarSecao(document, "RESUMO PROFISSIONAL", fonteSubtitulo);

            Paragraph resumo = new Paragraph(cv.getResumoProfissional(), fonteNormal);
            resumo.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(resumo);
        }

        // 4. EXPERIÊNCIAS PROFISSIONAIS
        if (cv.getExperiencias() != null && !cv.getExperiencias().isEmpty()) {
            document.add(Chunk.NEWLINE);
            adicionarSecao(document, "EXPERIÊNCIA PROFISSIONAL", fonteSubtitulo);

            for (Experiencia exp : cv.getExperiencias()) {
                adicionarExperiencia(document, exp, fonteNormalNegrito, fonteNormal);
            }
        }

        // 5. FORMAÇÃO ACADÊMICA
        if (cv.getFormacoes() != null && !cv.getFormacoes().isEmpty()) {
            document.add(Chunk.NEWLINE);
            adicionarSecao(document, "FORMAÇÃO ACADÊMICA", fonteSubtitulo);

            for (Formacao form : cv.getFormacoes()) {
                adicionarFormacao(document, form, fonteNormalNegrito, fonteNormal);
            }
        }

        // 6. HABILIDADES
        if (cv.getHabilidades() != null && !cv.getHabilidades().isEmpty()) {
            document.add(Chunk.NEWLINE);
            adicionarSecao(document, "HABILIDADES", fonteSubtitulo);

            PdfPTable tabelaHabilidades = new PdfPTable(3);
            tabelaHabilidades.setWidthPercentage(100);
            tabelaHabilidades.setSpacingBefore(10f);

            for (String habilidade : cv.getHabilidades()) {
                PdfPCell cellHabilidade = new PdfPCell(new Paragraph(habilidade, fonteNormal));
                cellHabilidade.setBorder(0);
                cellHabilidade.setPadding(5);
                tabelaHabilidades.addCell(cellHabilidade);
            }

            // Preencher células vazias se necessário
            int colunasVazias = 3 - (cv.getHabilidades().size() % 3);
            if (colunasVazias < 3) {
                for (int i = 0; i < colunasVazias; i++) {
                    PdfPCell cellVazia = new PdfPCell(new Paragraph(""));
                    cellVazia.setBorder(0);
                    tabelaHabilidades.addCell(cellVazia);
                }
            }

            document.add(tabelaHabilidades);
        }

        // 7. RODAPÉ
        document.add(Chunk.NEWLINE);
        Paragraph rodape = new Paragraph(
                "Currículo gerado automaticamente - " + new java.util.Date(),
                new Font(Font.HELVETICA, 9, Font.ITALIC, Color.GRAY)
        );
        rodape.setAlignment(Element.ALIGN_CENTER);
        document.add(rodape);

        document.close();
        return baos.toByteArray();
    }

    // ========== MÉTODOS AUXILIARES ==========

    private void adicionarCelulaContato(PdfPTable tabela, String texto, Font fonte) {
        PdfPCell cell = new PdfPCell(new Paragraph(texto, fonte));
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabela.addCell(cell);
    }

    private void adicionarSecao(Document document, String titulo, Font fonte) throws DocumentException {
        Paragraph secao = new Paragraph(titulo, fonte);
        secao.setSpacingAfter(10f);

        // Linha decorativa abaixo do título
        com.lowagie.text.pdf.draw.LineSeparator linha =
                new com.lowagie.text.pdf.draw.LineSeparator(1, 100, COR_SECUNDARIA, Element.ALIGN_LEFT, 0);

        document.add(secao);
        document.add(linha);
    }

    private void adicionarExperiencia(Document document, Experiencia exp,
                                      Font fonteNegrito, Font fonteNormal) throws DocumentException {

        // Cargo e Empresa
        Paragraph linha1 = new Paragraph();
        linha1.add(new Chunk(exp.getCargo() + " ", fonteNegrito));
        linha1.add(new Chunk("| " + exp.getEmpresa(),
                new Font(Font.HELVETICA, 11, Font.NORMAL, COR_SECUNDARIA)));
        document.add(linha1);

        // Período
        if (exp.getPeriodo() != null && !exp.getPeriodo().isEmpty()) {
            Paragraph periodo = new Paragraph(exp.getPeriodo(),
                    new Font(Font.HELVETICA, 10, Font.ITALIC, Color.GRAY));
            periodo.setSpacingBefore(2f);
            document.add(periodo);
        }

        // Descrição
        if (exp.getDescricao() != null && !exp.getDescricao().isEmpty()) {
            Paragraph descricao = new Paragraph(exp.getDescricao(), fonteNormal);
            descricao.setSpacingBefore(5f);
            descricao.setSpacingAfter(15f);
            document.add(descricao);
        }
    }

    private void adicionarFormacao(Document document, Formacao form,
                                   Font fonteNegrito, Font fonteNormal) throws DocumentException {

        // Curso e Instituição
        Paragraph linha1 = new Paragraph();
        linha1.add(new Chunk(form.getCurso() + " ", fonteNegrito));
        linha1.add(new Chunk("| " + form.getInstituicao(),
                new Font(Font.HELVETICA, 11, Font.NORMAL, COR_SECUNDARIA)));
        document.add(linha1);

        // Período/Ano
        String periodoTexto = "";
        if (form.getPeriodo() != null && !form.getPeriodo().isEmpty()) {
            periodoTexto = form.getPeriodo();
        } else if (form.getAnoConclusao() != null && !form.getAnoConclusao().isEmpty()) {
            periodoTexto = "Concluído em " + form.getAnoConclusao();
        }

        if (!periodoTexto.isEmpty()) {
            Paragraph periodo = new Paragraph(periodoTexto,
                    new Font(Font.HELVETICA, 10, Font.ITALIC, Color.GRAY));
            periodo.setSpacingBefore(2f);
            periodo.setSpacingAfter(15f);
            document.add(periodo);
        }
    }
}
