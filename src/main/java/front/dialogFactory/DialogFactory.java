package front.dialogFactory;

import front.fields.InterfacciaFields;
import javafx.scene.control.Dialog;


public interface DialogFactory {

    public Dialog<?> creaDialog();

    public InterfacciaFields creaFields();

    public void aggiungiRisultato();
}
