package Report;


import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class ReportGenerator {

    public static void generateReport(List<DescriptiveStatistics> statsList, String[] years, String[] imagePaths, String outputPath) throws Exception {
        XWPFDocument document = new XWPFDocument();

        for (int i = 0; i < years.length; i++) {
            DescriptiveStatistics stats = statsList.get(i);

            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("Year: " + years[i]);
            run.addBreak();

            run.setText("Median: " + stats.getPercentile(50));
            run.addBreak();
            run.setText("Mean: " + stats.getMean());
            run.addBreak();
            run.setText("1st Quartile: " + stats.getPercentile(25));
            run.addBreak();
            run.setText("3rd Quartile: " + stats.getPercentile(75));
            run.addBreak();
            run.setText("Standard Deviation: " + stats.getStandardDeviation());
            run.addBreak();
            run.setText("Minimum: " + stats.getMin());
            run.addBreak();
            run.setText("Maximum: " + stats.getMax());
            run.addBreak();

            XWPFParagraph imgParagraph = document.createParagraph();
            XWPFRun imgRun = imgParagraph.createRun();
            imgRun.addBreak();
            try (FileInputStream is = new FileInputStream(imagePaths[i])) {
                imgRun.addPicture(is, Document.PICTURE_TYPE_PNG, imagePaths[i], Units.toEMU(500), Units.toEMU(300));
            }
        }

        try (OutputStream os = new FileOutputStream(outputPath)) {
            document.write(os);
        }
    }
}
