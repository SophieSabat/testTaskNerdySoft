public class VirtualProductCodeManager {
    private static VirtualProductCodeManager instance;
    private String usedCode;

    public VirtualProductCodeManager() {
        usedCode = null;
    }

    public static synchronized VirtualProductCodeManager getInstance() {
        if (instance == null) {
            instance = new VirtualProductCodeManager();
        }
        return instance;
    }

    public void useCode(String code) {
        usedCode = code;
    }

    public boolean isCodeUsed(String code) {
        return code.equals(usedCode);
    }
}
