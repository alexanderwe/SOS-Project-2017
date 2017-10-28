package socketIo;

public interface SocketEventListener {
    void socketConnected();
    void socketDisconnected();
    void socketHasSentData(Object data);
    void retrievedEffectorData(Object... data);
    void retrievedContextData(Object... data);
}
