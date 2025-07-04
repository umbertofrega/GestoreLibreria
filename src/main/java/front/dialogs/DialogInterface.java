package front.dialogs;

import front.fields.InterfacciaFields;
import javafx.scene.control.Dialog;

public interface DialogInterface {

    /**
     * Metodo creazione effettiva del dialog
     * @return Il dialog che si vuole creare
     */
     Dialog<?> creaDialog();

    /**
     * Permette di creare i fields di testo del dialog, si lascia vuoto se non sono presenti fields
     * @return Un generico wrapper di fields.
     */
     InterfacciaFields creaFields();

    /**
     * Permette di aggiungere le funzione di ritorno di un'oggetto di tipo a scelta
     */
     void aggiungiRisultato();
}
