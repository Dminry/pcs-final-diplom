package server;


public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    public String getPdfName() {
        return pdfName;
    }

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(PageEntry pageEntry) {
        int result = pageEntry.count - this.count;
        if (result == 0) {
            result = this.pdfName.compareTo(pageEntry.pdfName);
            if (result == 0) {
                result = this.page - pageEntry.page;
            }
        }
        return result;
    }

    public String toString() {
        return "pdf= " + pdfName + " page= " + page + " count= " + count;
    }


}
