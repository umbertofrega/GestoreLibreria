package front.dialogFactory;

import front.fields.AbstractField;
import javafx.scene.control.Dialog;


public interface DialogFactory {

    public Dialog<?> creaDialog();

    public AbstractField creaFields();

    public void aggiungiRisultato();

}
