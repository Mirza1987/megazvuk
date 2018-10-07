package mzb.controller;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldInputLimiter extends PlainDocument{

	/** Limits the String length that can be entered in the TextField/TextArea
	 * 
	 */
	private static final long serialVersionUID = -7240253880988607875L;
	private int limit;
	
	public JTextFieldInputLimiter(int limitation){
		this.limit = limitation;
	}
	
	public void insertString(int offset, String str, AttributeSet set) throws BadLocationException{
		if(str == null){
			return;
		}else if((getLength() + str.length()) <= limit){
			super.insertString(offset, str, set);
		}
	}
	
}
