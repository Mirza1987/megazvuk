package mzb.model;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class Bill {

	private JTextPane toWhomTP = new JTextPane();
	private JTextPane adressTP = new JTextPane();
	private JTextPane zipCityTP = new JTextPane();
	private JTextField toWhomTF = new JTextField();
	private JTextField addressTF = new JTextField();
	private JTextField zipCityTF = new JTextField();

	private JTextPane eventNameTP = new JTextPane();
	private JTextArea subjectTF = new JTextArea();

	private JTextPane descriptonTP = new JTextPane();
	private JTextArea descriptonTF = new JTextArea();

	private JTextPane priceTP = new JTextPane();
	private JTextField priceTF = new JTextField();

//	private JTextField eventDateTP = new JTextField();
//	private JTextField eventDateTF = new JTextField();
//	private JTextArea checkDateTF = new JTextArea();
	private JTextField eventNameTF = new JTextField();
	private JTextField checkIdTF = new JTextField();
	private JTextField factureId = new JTextField();


	private JButton createButton = new JButton();

	private ButtonGroup billTypeGroup = new ButtonGroup();
	private JRadioButton quotation = new JRadioButton("Predračun");
	private JRadioButton check = new JRadioButton("Račun");

	public void groupButton() {
		billTypeGroup.add(quotation);
		billTypeGroup.add(check);
	}

	UtilDateModel modelEvent = new UtilDateModel();
	JDatePanelImpl datePanelEvent = new JDatePanelImpl(modelEvent);
	JDatePickerImpl eventDatePicker = new JDatePickerImpl(datePanelEvent);

	UtilDateModel modelBill = new UtilDateModel();
	JDatePanelImpl datePanelBill = new JDatePanelImpl(modelBill);
	JDatePickerImpl billDatePicker = new JDatePickerImpl(datePanelBill);

	public JDatePickerImpl getBillDatePicker() {
		return billDatePicker;
	}

	public void setBillDatePicker(JDatePickerImpl billDatePicker) {
		this.billDatePicker = billDatePicker;
	}

	public JDatePickerImpl getEventDatePicker() {
		return eventDatePicker;
	}

	public void setEventDatePicker(JDatePickerImpl eventDatePicker) {
		this.eventDatePicker = eventDatePicker;
	}

	// ========================================================
	// =================== Constructor ========================
	// ========================================================
	public Bill() {
		groupButton();
	}

	// ========================================================
	// =============== Getters and Setters ====================
	// ========================================================
	public JTextField getEventNameTF() {
		return eventNameTF;
	}

	public void setEventNameTF(JTextField eventNameTF) {
		this.eventNameTF = eventNameTF;
	}

	public JTextField getFactureId() {
		return factureId;
	}

	public void setFactureId(JTextField factureId) {
		this.factureId = factureId;
	}

	public JTextField getCheckIdTF() {
		return checkIdTF;
	}

	public void setCheckIdTF(JTextField checkIdTF) {
		this.checkIdTF = checkIdTF;
	}

//	public JTextArea getCheckDateTF() {
//		return checkDateTF;
//	}
//
//	public void setCheckDateTF(JTextArea checkDateTF) {
//		this.checkDateTF = checkDateTF;
//	}
//
//	public JTextField getEventDateTP() {
//		return eventDateTP;
//	}
//
//	public void setEventDateTP(JTextField eventDateTP) {
//		this.eventDateTP = eventDateTP;
//	}
//
//	public JTextField getEventDateTF() {
//		return eventDateTF;
//	}
//
//	public void setEventDateTF(JTextField eventDateTF) {
//		this.eventDateTF = eventDateTF;
//	}

	public JRadioButton getQuotation() {
		return quotation;
	}

	public JRadioButton getCheck() {
		return check;
	}

	public JTextField getPriceTF() {
		return priceTF;
	}

	public void setPriceTF(JTextField priceTF) {
		this.priceTF = priceTF;
	}

	public JTextPane getPriceTP() {
		priceTP.setText("Cijena");
		return priceTP;
	}

	public void setPriceTP(JTextPane priceTP) {
		this.priceTP = priceTP;
	}

	public JTextArea getDescriptonTF() {
		// descriptonTF.setSize(new Dimension(300, 300));
		return descriptonTF;
	}

	public void setDescriptonTF(JTextArea descriptonTF) {
		this.descriptonTF = descriptonTF;
	}

	public JTextPane getDescriptonTP() {
		descriptonTP.setText("Opis");
		return descriptonTP;
	}

	public void setDescriptonTP(JTextPane descriptonTP) {
		this.descriptonTP = descriptonTP;
	}

	public JTextArea getSubjectTF() {
		return subjectTF;
	}

	public void setSubjectTF(JTextArea eventNameTF) {
		this.subjectTF = eventNameTF;
	}

	public JTextPane getEventNameTP() {
		eventNameTP.setText("Manifestacija:");
		return eventNameTP;
	}

	public void setEventNameTP(JTextPane eventNameTP) {
		this.eventNameTP = eventNameTP;
	}

	public JTextField getZipCityTF() {
		return zipCityTF;
	}

	public void setZipCityTF(JTextField zipCityTF) {
		this.zipCityTF = zipCityTF;
	}

	public JTextPane getToWhomTP() {
		toWhomTP.setText("Klijent: ");
		return toWhomTP;
	}

	public void setToWhomTP(JTextPane toWhomTP) {
		this.toWhomTP = toWhomTP;
	}

	public JTextPane getAdressTP() {
		adressTP.setText("Adresa klijenta (ulica i broj):");
		return adressTP;
	}

	public void setAddressTP(JTextPane adressTP) {
		this.adressTP = adressTP;
	}

	public JTextPane getZipCityTP() {
		zipCityTP.setText("Poštanski broj i grad:");
		return zipCityTP;
	}

	public void setZipCityTP(JTextPane zipCityTP) {
		this.zipCityTP = zipCityTP;
	}

	public JTextField getToWhomTF() {
		return toWhomTF;
	}

	public void setToWhomTF(JTextField toWhomTF) {
		this.toWhomTF = toWhomTF;
	}

	public JTextField getAddressTF() {
		return addressTF;
	}

	public void setAdressTF(JTextField adressTF) {
		this.addressTF = adressTF;
	}

	public JButton getCreateButton() {
		createButton.setText("Kreiraj račun...");
		return createButton;
	}

	public void setCreateButton(JButton createButton) {
		this.createButton = createButton;
	}

}
