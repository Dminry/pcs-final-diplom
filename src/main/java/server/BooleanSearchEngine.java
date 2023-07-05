package server;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class BooleanSearchEngine implements SearchEngine {
    private Map<String, List<PageEntry>> wordsStatistics = new HashMap<>();


    public BooleanSearchEngine(File pdfsDir) throws IOException {
        for (File pdfFile : pdfsDir.listFiles()) {
            String pdfName = pdfFile.getName();
            var doc = new PdfDocument(new PdfReader(pdfFile));
            for (int i = 1; i < doc.getNumberOfPages(); i++) {
                int pageNumber = i;
                var page = doc.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(page);
                var words = text.split("\\P{IsAlphabetic}+");
                Map<String, Integer> inOnePage = new HashMap<>();
                for (String word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    inOnePage.put(word, inOnePage.getOrDefault(word, 0) + 1);
                }
                inOnePage.forEach((word, freq) -> {
                            if (!wordsStatistics.containsKey(word)) {
                                List<PageEntry> initialList = new ArrayList<>();
                                initialList.add(new PageEntry(pdfName, pageNumber, freq));
                                wordsStatistics.put(word, initialList);
                            } else {
                                List<PageEntry> listWithContent = wordsStatistics.get(word);
                                listWithContent.add(new PageEntry(pdfName, pageNumber, freq));
                                wordsStatistics.put(word, listWithContent);
                            }
                        }
                );
            }
        }
    }

    @Override
    public List<PageEntry> search(String words) throws NullPointerException {
        String[] splitWords = words.split(" ");
        if (splitWords.length == 1) {
            List<PageEntry> pageEntryList = wordsStatistics.getOrDefault(words.toLowerCase(), new ArrayList<>());
            if (pageEntryList.isEmpty()) {
                out.println("Такого слова в файлах нет ");
            }
            Collections.sort(pageEntryList);
            return pageEntryList;

        } else {
            List<String> wordsForSearch = Arrays.asList(splitWords);
            List<String> filteredWordsForSearch = wordsForSearch.stream()
                    .collect(Collectors.toList());
            Map<PdfPage, Integer> dictionary = new HashMap<>();
            for (String filteredWord : filteredWordsForSearch) {
                List<PageEntry> listForOneWord = wordsStatistics.get(filteredWord.toLowerCase());
                for (PageEntry pageEntry : listForOneWord) {
                    PdfPage pdfPage = new PdfPage(pageEntry.getPdfName(), pageEntry.getPage());
                    if (dictionary.containsKey(pdfPage)) {
                        Integer count = dictionary.get(pdfPage);
                        Integer newCount = count + pageEntry.getCount();
                        dictionary.put(pdfPage, newCount);
                    } else {
                        dictionary.put(pdfPage, pageEntry.getCount());
                    }
                }
            }
            List<PageEntry> generalPageEntryList = new ArrayList<>();
            dictionary.forEach(
                    (pdfPage, count) -> generalPageEntryList
                            .add(new PageEntry(pdfPage.getPdfName(),
                                    pdfPage.getPage(),
                                    count)));
            Collections.sort(generalPageEntryList);
            return generalPageEntryList;
        }
    }


}


