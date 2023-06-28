package server;

import java.util.Objects;

public class PdfPage {
    private final String pdfName;
    private final int page;

    public PdfPage(String pdfName, int page) {
        this.pdfName = pdfName;
        this.page = page;
    }

    public String getPdfName() {
        return pdfName;
    }

    public int getPage() {
        return page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PdfPage pageEntry = (PdfPage) o;
        return page == pageEntry.getPage() && pdfName.equals(pageEntry.getPdfName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(pdfName, page);
    }
}