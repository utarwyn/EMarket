package emarket.ihm.objets.formulaire;

import emarket.ihm.objets.Formulaire;

import javax.swing.*;

/**
 * Classe qui représente un objet de la classe Formulaire
 */
public abstract class ObjetFormulaire {

	protected Formulaire formulaire;

	private int     displayNbCol;
	private boolean inGroup;
	private int     width;


	public ObjetFormulaire(Formulaire form, int displayNbCol, int width) {
		this.formulaire   = form;
		this.displayNbCol = displayNbCol;
		this.width        = width;
		this.inGroup      = false;
	}


	public int     getDisplayNbCol() { return this.displayNbCol; }
	public int     getWidth       () { return this.width;        }
	public boolean isInGroup      () { return this.inGroup;      }


	public void setWidth  (int     width  ) { this.width   = width;   }
	public void setInGroup(boolean inGroup) { this.inGroup = inGroup; }


	/**
	 * Méthode appelée lorsque l'objet doit être généré dan le formulaire
	 * @return Un JPanel contenant l'objet à générer
	 */
	public abstract JPanel generer();

}
