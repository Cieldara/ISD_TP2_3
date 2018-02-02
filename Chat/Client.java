
import java.rmi.*;
import java.util.Objects;

public class Client implements Accounting_itf {

    private String name;

    public Client(String name) {
        this.name = name;
    }

    public void setName(String name) throws RemoteException {
        this.name = name;
    }

    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
