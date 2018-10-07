package mzb.view;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import mzb.controller.Execute;
import mzb.controller.JTextFieldInputLimiter;
import mzb.model.Bill;

public class Form extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Bill bill = new Bill();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form frame = new Form();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Form() {
		super("MegaZvuk - kreator računa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 371, 515);
		getContentPane().setLayout(null);

		// client
		JLabel lblKlijent = new JLabel("Klijent:");
		lblKlijent.setBounds(10, 11, 46, 14);
		getContentPane().add(lblKlijent);
		bill.getToWhomTF().setBounds(129, 8, 215, 20);
		getContentPane().add(bill.getToWhomTF());
		bill.getToWhomTF().setColumns(10);

		// address
		JLabel lblAddress = new JLabel("Adresa:");
		lblAddress.setBounds(10, 36, 46, 14);
		getContentPane().add(lblAddress);
		bill.getAddressTF().setBounds(129, 33, 215, 20);
		getContentPane().add(bill.getAddressTF());
		bill.getAddressTF().setColumns(10);

		// zip
		JLabel lblzipCity = new JLabel("Poštanski broj i grad:");
		lblzipCity.setBounds(10, 61, 128, 14);
		getContentPane().add(lblzipCity);
		bill.getZipCityTF().setBounds(148, 58, 116, 20);
		getContentPane().add(bill.getZipCityTF());
		bill.getZipCityTF().setColumns(10);

		// RADIOBUTTONS
		bill.getQuotation().setBounds(80, 134, 91, 23);
		getContentPane().add(bill.getQuotation());
		bill.getCheck().setBounds(180, 134, 69, 23);
		getContentPane().add(bill.getCheck());

		// check ID
		JLabel lblBrojRauna = new JLabel("Broj računa:");
		lblBrojRauna.setBounds(10, 165, 128, 14);
		getContentPane().add(lblBrojRauna);
		bill.getCheckIdTF().setBounds(148, 165, 116, 20);
		getContentPane().add(bill.getCheckIdTF());

		// facture ID
		JLabel lblFactureId = new JLabel("Broj fakture:");
		lblFactureId.setBounds(10, 191, 128, 14);
		getContentPane().add(lblFactureId);
		bill.getFactureId().setBounds(148, 191, 116, 20);
		getContentPane().add(bill.getFactureId());

		// event name
		JLabel lblEventName = new JLabel("Manifestacija:");
		lblEventName.setBounds(10, 217, 128, 14);
		getContentPane().add(lblEventName);
		bill.getEventNameTF().setBounds(148, 217, 116, 20);
		getContentPane().add(bill.getEventNameTF());

		// subject
		JLabel lblSubject = new JLabel("Predmet:");
		lblSubject.setBounds(10, 245, 91, 40);
		getContentPane().add(lblSubject);
		bill.getSubjectTF().setDocument(new JTextFieldInputLimiter(91));
		bill.getSubjectTF().setLineWrap(true);
		bill.getSubjectTF().setBounds(92, 245, 253, 40);

		// scrollbar in Subject Text Area - connected with subcjet text field!
		JScrollPane sp = new JScrollPane(bill.getSubjectTF(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setBounds(92, 245, 253, 40);
		getContentPane().add(sp);

		// description
		JLabel lblDescription = new JLabel("Opis:");
		lblDescription.setBounds(10, 279, 46, 14);
		getContentPane().add(lblDescription);
		bill.getDescriptonTF().setDocument(new JTextFieldInputLimiter(320));
		bill.getDescriptonTF().setLineWrap(true);
		bill.getDescriptonTF().setBounds(11, 296, 334, 132);
		bill.getDescriptonTF().setBorder(new JTextField().getBorder());
		getContentPane().add(bill.getDescriptonTF());

		// price
		JLabel lblCijena = new JLabel("Cijena:");
		lblCijena.setBounds(10, 440, 46, 14);
		getContentPane().add(lblCijena);
		bill.getPriceTF().setBounds(52, 437, 91, 20);
		getContentPane().add(bill.getPriceTF());
		bill.getPriceTF().setColumns(10);

		// event date
		JLabel lblEventDate = new JLabel("Datum manifestacije: ");
		lblEventDate.setBounds(10, 86, 128, 14);
		getContentPane().add(lblEventDate);
		bill.getEventDatePicker().setBounds(148, 83, 130, 20);
		getContentPane().add(bill.getEventDatePicker());

		// bill date
		JLabel lblDatumRacuna = new JLabel("Datum računa:");
		lblDatumRacuna.setBounds(10, 111, 128, 14);
		getContentPane().add(lblDatumRacuna);
		bill.getBillDatePicker().setBounds(148, 108, 130, 20);
		getContentPane().add(bill.getBillDatePicker());

		// BUTTON
		JButton execute = new JButton("Kreiraj račun...");
		execute.setBounds(205, 430, 140, 35);
		getContentPane().add(execute);

		JLabel lblKm = new JLabel(",- KM");
		lblKm.setBounds(149, 440, 46, 14);
		getContentPane().add(lblKm);

		execute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (!bill.getPriceTF().getText().equals("")) {
					if (!bill.getPriceTF().getText().matches("[-+]?[0-9]*\\,?[0-9]+")) {
						JOptionPane.showMessageDialog(null,
								"Pogrešan unos u polje cijene - odvajati decimalne brojeve sa zarezom (,)");
					} else {
						try {
							String msg = Execute.checkFields(bill);
							if (!Execute.checkFields(bill).equals("")) {
								JOptionPane.showMessageDialog(null, msg);
							} else {
								Execute.createDocument(bill);
								JOptionPane.showMessageDialog(null, "Dokument je kreiran");
								Execute.cleanFields(bill);
							}
						} catch (IOException | InvalidFormatException e) {
							e.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Popunite polje cijena");
				}

			}
		});

	}
}
