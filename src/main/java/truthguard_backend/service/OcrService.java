package truthguard_backend.service;

import com.sun.jna.NativeLibrary;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class OcrService {

    static {
        NativeLibrary.addSearchPath("tesseract", "/opt/homebrew/lib");
    }

    public String extractText(File file) {

        try {

            Tesseract tesseract = new Tesseract();

            tesseract.setDatapath("/opt/homebrew/share/tessdata");

            tesseract.setLanguage("eng");

            return tesseract.doOCR(file);

        } catch (Exception e) {

            e.printStackTrace();

            return e.getMessage();
        }
    }
}