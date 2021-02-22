package com.amtSoftware.vistaControlador;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextMarginFinder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class VentanaPrincipal extends VBox {

    @FXML
    private MenuItem nuevo;
    @FXML
    private MenuItem cerrar;
    @FXML
    private TextField invoiceNumber;
    @FXML
    private TextField purchaseOrderNumber;
    @FXML
    private TextField packingSlip;
    @FXML
    private TextField numberOfBoxes;
    @FXML
    private TextField carrier;
    @FXML
    private TextField incoterm;
    @FXML
    private TextField weight;
    @FXML
    private Button generaButton;

    public VentanaPrincipal() throws IOException {
        ClassLoader cargaClase = getClass().getClassLoader();
        FXMLLoader cargaFXML = new FXMLLoader(cargaClase.getResource("VentanaPrincipal.fxml"));
        cargaFXML.setRoot(this);
        cargaFXML.setController(this);
        cargaFXML.load();
        inicializar();
    }

    private void generaPdf(File original) throws Exception {

        String path = original.getAbsolutePath();
        String name = FilenameUtils.removeExtension(path);

        Font header = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Font details = new Font(Font.FontFamily.HELVETICA, 8);

        Document document = new Document(PageSize.LETTER);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(name + "-CB.pdf"));
        document.open();

        float totalWidth = 200;
        float fixedHeight = 80;

        float x = 110;
        float y = 110;

        PdfPTable supplierTable = new PdfPTable(1);
        supplierTable.setTotalWidth(totalWidth);
        supplierTable.setLockedWidth(true);

        PdfPTable invoiceTable = new PdfPTable(1);
        invoiceTable.setTotalWidth(totalWidth);
        invoiceTable.setLockedWidth(true);

        PdfPTable billToTable = new PdfPTable(1);
        billToTable.setTotalWidth(totalWidth);
        billToTable.setLockedWidth(true);

        PdfPTable shipToTable = new PdfPTable(1);
        shipToTable.setTotalWidth(totalWidth);
        billToTable.setLockedWidth(true);

        PdfPTable poTable = new PdfPTable(1);
        poTable.setTotalWidth(totalWidth);
        poTable.setLockedWidth(true);

        PdfPTable packingSlipTable = new PdfPTable(1);
        packingSlipTable.setTotalWidth(totalWidth);
        packingSlipTable.setLockedWidth(true);

        PdfContentByte contentByte = writer.getDirectContent();

        PdfPCell supplierCell = new PdfPCell();
        supplierCell.addElement(new Phrase("Supplier", header));
        supplierCell.addElement(new Phrase("CMA AUTOMOTIVE S.A. DE C.V.", details));
        supplierCell.addElement(new Phrase("Circuito Japón # 117", details));
        supplierCell.addElement(new Phrase("San Francisco de los Romos Centro", details));
        supplierCell.addElement(new Phrase("San Francisco de los Romo, Aguascalientes 20300", details));
        supplierCell.setFixedHeight(fixedHeight);
        supplierCell.setBorder(Rectangle.BOX);
        supplierCell.setBorderWidth(2);
        supplierTable.addCell(supplierCell);

        PdfPCell invoiceCell = new PdfPCell();
        invoiceCell.addElement(new Phrase("Invoice#: " + invoiceNumber.getText(), header));
        invoiceCell.addElement(new Phrase(" ", new Font(Font.FontFamily.HELVETICA, 5)));
        Barcode128 invoiceBC = new Barcode128();
        invoiceBC.setCodeType(Barcode.CODE128);
        invoiceBC.setCode("IN" + invoiceNumber.getText());
        invoiceBC.setSize(8);
        Image invoiceBCImage = invoiceBC.createImageWithBarcode(contentByte, null, null);
        invoiceBCImage.scaleAbsolute(x, y);
        invoiceBCImage.setAlignment(Element.ALIGN_MIDDLE);
        invoiceCell.addElement(invoiceBCImage);
        invoiceCell.setFixedHeight(fixedHeight);
        invoiceCell.setBorder(Rectangle.BOX);
        invoiceCell.setBorderWidth(2);
        invoiceTable.addCell(invoiceCell);

        PdfPCell billToCell = new PdfPCell();
        billToCell.addElement(new Phrase("Bill To", header));
        billToCell.addElement(new Phrase("FLEXTRONICS INTERNATIONAL", details));
        billToCell.addElement(new Phrase("EUROPE B.V.", details));
        billToCell.addElement(new Phrase("TAX ID: 807985752", details));
        billToCell.setFixedHeight(fixedHeight);
        billToCell.setBorder(Rectangle.BOX);
        billToCell.setBorderWidth(2);
        billToTable.addCell(billToCell);

        PdfPCell shipToCell = new PdfPCell();
        shipToCell.addElement(new Phrase("ShipTo", header));
        shipToCell.addElement(new Phrase("FLEXTRONICS MANUFACTURING", details));
        shipToCell.addElement(new Phrase("AGUASCALIENTES, S.A. DE C.V.", details));
        shipToCell.addElement(new Phrase("BOULEVARD A ZACATECAS KM. 9.5", details));
        shipToCell.addElement(new Phrase("JESUS MARIA, AGS. 20900", details));
        shipToCell.setFixedHeight(fixedHeight);
        shipToCell.setBorder(Rectangle.BOX);
        shipToCell.setBorderWidth(2);
        shipToTable.addCell(shipToCell);

        PdfPCell poCell = new PdfPCell();
        poCell.addElement(new Phrase("PO:", header));
        poCell.addElement(new Phrase(" ", new Font(Font.FontFamily.HELVETICA, 5)));
        Barcode128 poBC = new Barcode128();
        poBC.setCodeType(Barcode.CODE128);
        poBC.setCode("K" + purchaseOrderNumber.getText());
        poBC.setSize(8);
        Image poBCImage = poBC.createImageWithBarcode(contentByte, null, null);
        poBCImage.scaleAbsolute(x, y);
        poBCImage.setAlignment(Element.ALIGN_MIDDLE);
        poCell.addElement(poBCImage);
        poCell.setFixedHeight(fixedHeight);
        poCell.setBorder(Rectangle.NO_BORDER);
        poTable.addCell(poCell);

        PdfPCell packingSlipCell = new PdfPCell();
        packingSlipCell.addElement(new Phrase("Packing Slip:", header));
        packingSlipCell.addElement(new Phrase(" ", new Font(Font.FontFamily.HELVETICA, 5)));
        Barcode128 packingSlipBC = new Barcode128();
        packingSlipBC.setCodeType(Barcode.CODE128);
        packingSlipBC.setCode("PS" + packingSlip.getText());
        packingSlipBC.setSize(8);
        Image packingSlipBCImage = packingSlipBC.createImageWithBarcode(contentByte, null, null);
        packingSlipBCImage.scaleAbsolute(x, y);
        packingSlipBCImage.setAlignment(Element.ALIGN_MIDDLE);
        packingSlipCell.addElement(packingSlipBCImage);
        packingSlipCell.setFixedHeight(fixedHeight);
        packingSlipCell.setBorder(Rectangle.NO_BORDER);
        packingSlipTable.addCell(packingSlipCell);

        PdfReader reader = new PdfReader(new FileInputStream(path));
        PdfImportedPage importedPage;
        for (int i = 0; i < reader.getNumberOfPages(); i++) {
            importedPage = writer.getImportedPage(reader, i + 1);
            document.newPage();
            contentByte.addTemplate(importedPage, i, i);
        }

        PdfReaderContentParser contentParser = new PdfReaderContentParser(reader);
        TextMarginFinder marginFinder = contentParser.processContent(reader.getNumberOfPages(), new TextMarginFinder());

        float clausula1 = marginFinder.getLly() - 15;
        float clausula2 = marginFinder.getLly() - supplierTable.getTotalHeight() - 30;
        float clausula3 = marginFinder.getLly() - (poTable.getTotalHeight() * 2) - 30;

        if (clausula1 >= supplierTable.getTotalHeight() && clausula2 >= billToTable.getTotalHeight() && clausula3 >= poTable.getTotalHeight()) {

            supplierTable.writeSelectedRows(0, -1,
                    80, clausula1, contentByte);
            invoiceTable.writeSelectedRows(0, -1,
                    320, clausula1, contentByte);

            billToTable.writeSelectedRows(0, -1,
                    80, clausula2, contentByte);
            shipToTable.writeSelectedRows(0, -1,
                    320, clausula2, contentByte);

            poTable.writeSelectedRows(0, -1,
                    80, clausula3, contentByte);
            packingSlipTable.writeSelectedRows(0, -1,
                    320, clausula3, contentByte);

        }

        if (clausula1 < supplierTable.getTotalHeight() || clausula2 < billToTable.getTotalHeight() || clausula3 < poTable.getTotalHeight()) {

            document.newPage();

            supplierTable.writeSelectedRows(0, -1,
                    80, document.top(), contentByte);
            invoiceTable.writeSelectedRows(0, -1,
                    320, document.top(), contentByte);

            billToTable.writeSelectedRows(0, -1,
                    80, (document.top() - billToTable.getTotalHeight() - 15), contentByte);
            shipToTable.writeSelectedRows(0, -1,
                    320, (document.top() - billToTable.getTotalHeight() - 15), contentByte);

            poTable.writeSelectedRows(0, -1,
                    80, (document.top() - (billToTable.getTotalHeight() * 2) - 15), contentByte);
            packingSlipTable.writeSelectedRows(0, -1,
                    320, (document.top() - (billToTable.getTotalHeight() * 2) - 15), contentByte);
        }


        document.close();

        System.out.println("1er:" + (marginFinder.getLly() - 15) + " objeto: " + supplierTable.getTotalHeight());
        System.out.println("2do:" + (marginFinder.getLly() - (supplierTable.getTotalHeight()) - 30) + " objeto: " + invoiceTable.getTotalHeight());

    }

    private void cargaPdf() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File archivoSeleccionado = fileChooser.showOpenDialog(null);
        if (archivoSeleccionado != null) {
            System.out.println(archivoSeleccionado.getName());
            try {
                generaPdf(archivoSeleccionado);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Archivo inválido");
        }
    }

    private void inicializar() {
        this.numberOfBoxes.setDisable(true);
        this.carrier.setDisable(true);
        this.incoterm.setDisable(true);
        this.weight.setDisable(true);
        this.generaButton.setOnAction(event -> {
            if (this.packingSlip.getText().equals("") || this.purchaseOrderNumber.getText().equals("")
                    || this.invoiceNumber.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Por favor llene los campos correspondientes.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    return;
                }
            } else {
                cargaPdf();
            }
        });
    }
}
