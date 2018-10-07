package mzb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;

import mzb.model.Bill;

public class Execute {

	private static XWPFDocument document;
	private static FileOutputStream out = null;
	private static XWPFParagraph paragraph;
	private static XWPFRun run;

	public static void createDocument(Bill bill) throws IOException, InvalidFormatException {

		String docName = createFilename(bill);
		File dest = new File("D:\\Dropbox\\Dedic Home\\Računi\\Generisani računi\\" + docName + ".doc");

		document = new XWPFDocument();
		paragraph = document.createParagraph();
		run = paragraph.createRun();

		try {
			out = new FileOutputStream(dest);
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}

		createHeader(document, paragraph);
		writeCompanyInfo();
		writeClientInfo(bill);
		writeCheckId(bill);
		writeSubject(bill);
		writeDescription(bill);
		writePrice(bill);
		writeCityAndDate(bill);
		writeSignLine();

		try {
			document.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeSignLine() throws InvalidFormatException, FileNotFoundException, IOException {

		String imgFile = "Resources\\sign.png";

		XWPFParagraph paragraphSignLine = document.createParagraph();
		paragraphSignLine.setAlignment(ParagraphAlignment.RIGHT);
		run = paragraphSignLine.createRun();
		run.addPicture(new FileInputStream(imgFile), XWPFDocument.PICTURE_TYPE_PNG, imgFile, Units.toEMU(195),
				Units.toEMU(33));
	}

	private static void writeCityAndDate(Bill bill) {
		XWPFParagraph paragraphCityAndDate = document.createParagraph();
		run = paragraphCityAndDate.createRun();
		run.setFontSize(13);
		run.addBreak();
		run.setText("Bihać, " + dateFormatter((Date) bill.getBillDatePicker().getModel().getValue()) + ".god.");
	}

	private static void writePrice(Bill bill) {
		String price = convertPriceToWords(bill);

		int dotNumbers = 113 - price.length() - bill.getPriceTF().getText().length();
		int addDots = 0;

		String dots = "";
		// System.out.println(price.length());
		// System.out.println(bill.getPriceTF().getText().length());
		// System.out.println(bill.getPriceTF().getText().length() +
		// price.length());
		// System.out.println(dotNumbers);

		if (dotNumbers > 80) {
			addDots = 85;
		} else if (dotNumbers > 70) {
			addDots = 60;
		} else if (dotNumbers > 58) { // OK
			addDots = 31;
		} else if (dotNumbers > 55) { // OK
			addDots = 33;
		} else if (dotNumbers > 47) {
			addDots = 23;
		} else if (dotNumbers > 35) {
			addDots = 18;
		}

		for (int i = 0; i < addDots; i++) {
			dots = dots + ".";
		}

		// System.out.println("Dots: " + dots + " " + addDots);

		XWPFParagraph paragraphPriceTitle = document.createParagraph();
		run = paragraphPriceTitle.createRun();
		run.setFontSize(13);
		run.setText("Cijena za navedene usluge iznosi:");
		run = paragraphPriceTitle.createRun();
		run.setFontSize(13);
		// run.setColor("FFFFFF");
		run.addBreak();
		run = paragraphPriceTitle.createRun();
		run.setFontSize(13);
		run.setBold(true);
		run.setText("(" + price + " KM)" + dots + " " + bill.getPriceTF().getText() + " KM");
	}

	private static String convertPriceToWords(Bill bill) {

		String priceEntry = bill.getPriceTF().getText();
		String priceInWords = "";
		String decimalPrice = "";
		int numberAtPosition;
		int counter = 0;
		int isDecimal = priceEntry.indexOf(',');

		if (isDecimal != -1) {
			decimalPrice = priceEntry.substring(priceEntry.indexOf(','), priceEntry.length());
			priceEntry = priceEntry.substring(0, isDecimal);
		}

		for (int i = priceEntry.length() - 1; i >= 0; i--) {
			counter++;
			numberAtPosition = Integer.parseInt(String.valueOf(priceEntry.charAt(i)));
			switch (counter) {
			case 1:
				priceInWords = NumbersWords.singles[numberAtPosition] + priceInWords;
				break;
			case 2:
				if (numberAtPosition == 1) {
					int single = Integer.parseInt(String.valueOf(priceEntry.charAt(priceEntry.length() - 1)));
					priceInWords = NumbersWords.teens[single];

				} else {
					priceInWords = NumbersWords.tens[numberAtPosition] + priceInWords;
				}
				break;
			case 3:
				priceInWords = NumbersWords.hundreds[numberAtPosition] + priceInWords;
				break;
			case 4:
				priceInWords = NumbersWords.thousands[numberAtPosition] + priceInWords;
				break;
			case 5:
				priceInWords = NumbersWords.tenThousands[numberAtPosition] + priceInWords;
				break;
			}
		}

		if (isDecimal != -1) {
			priceInWords = priceInWords + " i " + decimalPrice.substring(1) + "/100";
		}

		return priceInWords;

	}

	private static void writeDescription(Bill bill) {
		XWPFParagraph paragraphDescription = document.createParagraph();
		run = paragraphDescription.createRun();
		breakIntoLines(bill.getDescriptonTF().getText(), paragraphDescription, "   ");
		run.setFontSize(13);
	}

	private static void writeSubject(Bill bill) {
		XWPFParagraph paragraphSubject = document.createParagraph();
		run = paragraphSubject.createRun();
		run.setBold(true);

		run.setFontSize(13);

		run.setBold(true);
		run.setText("PREDMET: ");

		run = paragraphSubject.createRun();
		run.setBold(false);
		run.setFontSize(13);
		run.setText(bill.getSubjectTF().getText());
	}

	private static void writeCheckId(Bill bill) {
		XWPFParagraph paragraphCheckId = document.createParagraph();
		run = paragraphCheckId.createRun();
		run.setBold(true);
		run.setUnderline(UnderlinePatterns.SINGLE);
		run.setFontSize(16);

		if (bill.getQuotation().isSelected()) {
			run.setText("Predračun: " + bill.getCheckIdTF().getText());
		} else {
			run.setText("Račun: " + bill.getCheckIdTF().getText());
		}
		run.addBreak();
		run.setText("BF: " + bill.getFactureId().getText());
	}

	private static void writeClientInfo(Bill bill) {
		XWPFParagraph paragraphClient = document.createParagraph();
		paragraphClient.setAlignment(ParagraphAlignment.RIGHT);
		run = paragraphClient.createRun();
		run.setFontSize(13);

		run.addBreak();
		run.setText(bill.getToWhomTF().getText());
		run.addBreak();
		run.setText(bill.getAddressTF().getText());
		run.addBreak();
		run.setText(bill.getZipCityTF().getText());

	}

	private static void writeCompanyInfo() {
		run.setFontSize(13);
		run.setBold(true);
		run.setText("GSM: 061-793-236");
		run.addBreak();
		run.setText("E-mail: ");

		run = paragraph.createRun();
		run.setBold(false);
		run.setFontSize(13);
		run.setUnderline(UnderlinePatterns.SINGLE);
		run.setText("mirsaddedic@live.com");

		run = paragraph.createRun();
		run.addBreak();
		run.setBold(true);
		run.setFontSize(13);
		run.setText("ID broj: 4363863550009");
		run.isBold();
		run.addBreak();
		run.setText("Broj računa: 3385002275164310 – UNICREDIT BANK-BIHAĆ");
		run.isBold();
	}

	public void test() throws IOException {
		System.out.println(ImageIO.read(getClass().getResourceAsStream("/memorandum.png")));
	}

	private static void createHeader(XWPFDocument document, XWPFParagraph paragraph)
			throws InvalidFormatException, FileNotFoundException, IOException {
		CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
		XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document, sectPr);

		XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

		paragraph = header.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.LEFT);

		CTTabStop tabStop = paragraph.getCTP().getPPr().addNewTabs().addNewTab();
		tabStop.setVal(STTabJc.CENTER);

		XWPFRun run = paragraph.createRun();

		// ===========

//		System.out.println(new File("Resources\\memorandum.png").getAbsolutePath());
//		String tmp = new File("Resources\\memorandum.png").getAbsolutePath().toString();
//		System.out.println(tmp);

		// ===========
		String imgFile = "Resources\\memorandum.png";
		run.addPicture(new FileInputStream(imgFile), XWPFDocument.PICTURE_TYPE_PNG, imgFile, Units.toEMU(470), Units.toEMU(55));

	}

	private static String createFilename(Bill bill) {
		return dateFormatter((Date) bill.getEventDatePicker().getModel().getValue()) + " - "
				+ bill.getEventNameTF().getText();
	}

	private static String dateFormatter(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		return formatter.format(date).toString();
	}

	private static void breakIntoLines(String data, XWPFParagraph paragraph, String seperator) {

		run = paragraph.createRun();
		if (data.contains("\n")) {
			String[] lines = data.split("\n");
			run.setText(lines[0], 0); // set first line into XWPFRun

			for (int i = 1; i < lines.length; i++) {
				// add break and insert new text
				run.addBreak();
				run.setText(lines[i]);
			}
		} else {
			run.setText(data, 0);
		}
	}

	public static String checkFields(Bill bill) {

		if (bill.getToWhomTF().getText().equals("")) {
			return "Popunite polje klijent";
		}
		if (bill.getAddressTF().getText().equals("")) {
			return "Popunite polje adresa";
		}
		if (bill.getZipCityTF().getText().equals("")) {
			return "Popunite polje poštanski broj i grad";
		}
		if (bill.getZipCityTF().getText().equals("")) {
			return "Popunite polje poštanski broj i grad";
		}
		if (dateFormatter((Date) bill.getEventDatePicker().getModel().getValue()).equals("")) {
			return "Popunite polje datum manifestacije";
		}
		if (dateFormatter((Date) bill.getBillDatePicker().getModel().getValue()).equals("")) {
			return "Popunite polje datum računa";
		}
		if (dateFormatter((Date) bill.getBillDatePicker().getModel().getValue()).equals("")) {
			return "Popunite polje datum računa";
		}
		if (!bill.getQuotation().isSelected() && !bill.getCheck().isSelected()) {
			return "Odaberite opciju račun ili predračun";
		}
		if (bill.getCheckIdTF().getText().equals("")) {
			return "Popunite polje broj računa";
		}
		if (bill.getFactureId().getText().equals("")) {
			return "Popunite polje broj fakture";
		}
		if (bill.getEventNameTF().getText().equals("")) {
			return "Popunite polje ime manifestacije";
		}
		if (bill.getSubjectTF().getText().equals("")) {
			return "Popunite polje predmet";
		}
		if (bill.getDescriptonTF().getText().equals("")) {
			return "Popunite polje opis";
		}

		return "";

	}

	public static void cleanFields(Bill bill) {

		bill.getToWhomTF().setText("");
		bill.getAddressTF().setText("");
		bill.getZipCityTF().setText("");
		bill.getCheckIdTF().setText("");
		bill.getFactureId().setText("");
		bill.getEventNameTF().setText("");
		bill.getDescriptonTF().setText("");
		bill.getPriceTF().setText("");
	}

}