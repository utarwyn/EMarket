package emarket.ihm.objets.formulaire;

import emarket.ihm.IHMConsts;
import emarket.ihm.interfaces.ISelectionChanged;
import emarket.ihm.objets.Formulaire;
import emarket.ihm.objets.ImagePreviewPanel;
import emarket.ihm.util.Fonts;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Champ extends ObjetFormulaire implements ActionListener, KeyListener, FocusListener {

	private String name;
	private String type;
	private String label;
	private String valeur;

	private JComponent _field;
	private JLabel     _label;
	private JPanel     _panel;

	private Pattern verifPattern;
	private boolean verified;
	private java.util.List<Predicate<Object>> predicates;

	private String[]          selectDatas;
	private ISelectionChanged selectionChanged;
	private ImagePreviewPanel previewPanel; // Juste pour les champs de type fichier


	public Champ(Formulaire form, String name, int displayNbCol, String label) {
		super(form, displayNbCol, 0);

		this.name        =   name;
		this.label       =  label;
		this.type        = "text";
		this.verified    =   true;
		this.selectDatas = new String[0];
		this.predicates  = new ArrayList<>();
	}


	public String  getName        () { return this.name;     }
	public String  getLabel       () { return this.label;    }
	public String  getValeur      () { return this.valeur;   }
	public boolean isVerified     () {
		return this.valeur != null && !this.valeur.isEmpty() && this.verified && this.testPredicates();
	}
	public JComponent getField    () { return this._field; }

	ImagePreviewPanel getPreviewPanel() { return this.previewPanel; }


	public void setType  (String type  ) { this.type   = type;   }
	public void setValeur(String valeur) {
		this.valeur = valeur;

		if (this._field instanceof JTextField)
			((JTextField) this._field).setText(this.valeur);
		else if (this._field instanceof JComboBox){
			JComboBox select = (JComboBox) this._field;

			for (int i = 0; i < select.getItemCount(); i++)
				if (select.getItemAt(i).toString().equals(this.valeur))
					select.setSelectedIndex(i);
		}

		this.testRegex();
	}
	public Champ setRegexVerification(String regex) {
		this.verifPattern = Pattern.compile(regex);
		return this;
	}
	public Champ setSelectDatas(String... datas) {
		this.selectDatas = datas;

		// On met à jour le champ si besoin
		if (this._field != null && this._field instanceof JComboBox) {
			JComboBox select = (JComboBox) this._field;

			select.removeAllItems();
			for (String s : datas) select.addItem(s);
		}

		return this;
	}
	public Champ attachPreviewPanel(ImagePreviewPanel previewPanel) { this.previewPanel = previewPanel; return this; }
	public Champ setSelectionChanged(ISelectionChanged selectionChanged) {
		this.selectionChanged = selectionChanged;
		return this;
	}
	public Champ addPredicate(Predicate<Object> p) {
		this.predicates.add(p);
		return this;
	}


	public void vider() { this.setValeur(""); }



	public JPanel generer() {
		if (this._panel != null) return this._panel;

		switch (this.type) {
			case "text"     : this._field = new JTextField(12);                break;
			case "password" : this._field = new JPasswordField(12);            break;
			case "select"   : this._field = new JComboBox<>(this.selectDatas); break;
			case "file"     : this._field = new ChampFichier(this);            break;
		}

		if (this._field == null) return new JPanel();

		// Attribution de la valeur
		if (this.valeur != null) this.setValeur(this.valeur);


		// Création du label et paramétrage du champ texte
		if (this.label != null) {
			this._label = new JLabel(this.label);
			this._label.setForeground(IHMConsts.TEXT_COLOR);
			this._label.setFont(Fonts.getFont("OpenSansSemibold").deriveFont(Font.PLAIN, 14.0f));
			this._label.setHorizontalAlignment(SwingConstants.LEFT);
		}

		if (this._field instanceof JTextField)
			this._field.addKeyListener(this);
		else if (this._field instanceof JComboBox) {
			JComboBox select = ((JComboBox) this._field);

			select.addActionListener(this);

			if (select.getItemCount() > 0) {
				select.setSelectedIndex(0);
				this.setValeur(select.getSelectedItem().toString());
			}
		}

		this._field.addFocusListener(this);

		// On créé le panel pour gérer le champ
		this._panel = new JPanel(new GridLayout(1, 2));
		this._panel.setOpaque(false);

		if (this._label != null) this._panel.add(this._label);
		this._panel.add(this._field);

		return this._panel;
	}


	/*   Évenements de gestion du champ   */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (this.selectionChanged != null) {
			JComboBox select = (JComboBox) actionEvent.getSource();
			this.selectionChanged.onSelectionChanged(select, select.getSelectedItem());
		}
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {  }
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		int keyCode = keyEvent.getKeyCode();

		// Touche "Entrée"
		if (keyCode == 10) {
			this.valeur = ((JTextField) this._field).getText();
			this.formulaire.envoyer();
		}
	}
	@Override
	public void keyReleased(KeyEvent keyEvent) {
		this.valeur = ((JTextField) this._field).getText();
		this.testRegex();
	}

	@Override
	public void focusGained(FocusEvent focusEvent) {  }
	@Override
	public void focusLost(FocusEvent focusEvent) {
		if (this._field instanceof JTextField)
			this.valeur = ((JTextField) this._field).getText();
		else if (this._field instanceof JComboBox)
			if (((JComboBox) this._field).getSelectedItem() != null)
				this.valeur = ((JComboBox) this._field).getSelectedItem().toString();
	}


	private void testRegex() {
		if (this.verifPattern != null) {
			boolean ok     = this.verifPattern.matcher(this.valeur).find();
			if (ok) ok = this.testPredicates();

			Border  border = BorderFactory.createLineBorder((ok) ? IHMConsts.OK_COLOR : IHMConsts.ERROR_COLOR, 3);

			this._field.setBorder(border);

			this.verified = ok;
		}
	}

	private boolean testPredicates() {
		for (Predicate<Object> p : this.predicates)
			if (!p.test(this.getValeur()))
				return false;

		return true;
	}
}
