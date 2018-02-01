import java.rmi.*;

public interface Chat_itf extends Remote, Join_itf, Message_itf, Leave_itf, Request_history_itf {
}
