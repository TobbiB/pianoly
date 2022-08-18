package grg.music.pianoly.gui.interfaces;

public interface IGUIIn {

    void setWaitingDeviceOut(String name);

    String deviceDetected(String name);

    void setupFinished();
}
