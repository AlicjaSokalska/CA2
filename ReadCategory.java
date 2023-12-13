package Parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import entities.Emission;

public class ReadCategory {
	private static final String PERSISTENCE_UNIT_NAME = "GHGEmission";

	public static void main(String[] args) {
		try {
			Document doc = Jsoup.connect("https://www.ipcc-nggip.iges.or.jp/EFDB/find_ef.php").get();

			Elements allScripts = doc.getElementsByTag("script");

			for (Element script : allScripts) {
				String scriptContent = script.html();

				if (scriptContent.contains("ipccTree = new dTree('ipccTree')")) {
					int startIndex = scriptContent.indexOf('(') + 1;
					int endIndex = scriptContent.lastIndexOf(')');
					String contentInsideParentheses = scriptContent.substring(startIndex, endIndex);

					String[] lines = contentInsideParentheses.split(",");

					List<Emission> emissions = new ArrayList<>();

					String currentCategory = "";

					for (String line : lines) {
						String trimmedLine = line.replaceAll("'", "").trim();

						if (!trimmedLine.contains("/EFDB/find_ef.php?ipcc_code=")) {
							String[] parts = trimmedLine.split("-");
							String category = parts[0].trim();
							String description = parts.length > 1 ? parts[1].trim() : "";

							if (category.startsWith(currentCategory)) {
								description = currentCategory + " - " + description;
							} else {
								currentCategory = category;
							}

							Emission emission = new Emission();
							emission.setCategory(category);
							emission.setDescription(description);

							emissions.add(emission);
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
