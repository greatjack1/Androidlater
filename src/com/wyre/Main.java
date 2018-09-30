package com.wyre;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            String currLang;
            if (args.length > 0) {
                currLang = args[0];
            } else {
                currLang = "en";
            }
            String path = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            String stringFilePath = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + "values/strings.xml";
            List<String> languages = new ArrayList<String>();
            languages.add(Languages.Hebrew);
            languages.add(Languages.Yiddish);
            languages.add(Languages.Afrikaans);
            languages.add(Languages.Albanian);
            languages.add(Languages.Amharic);
            languages.add(Languages.Arabic);
            languages.add(Languages.Armenian);
            languages.add(Languages.Azerbaijani);
            languages.add(Languages.Basque);
            languages.add(Languages.Belarusian);
            languages.add(Languages.Bengali);
            languages.add(Languages.Bosnian);
            languages.add(Languages.Bulgarian);
            languages.add(Languages.Catalan);
            languages.add(Languages.Cebuano);
            languages.add(Languages.Chichewa);
            languages.add(Languages.Chinese_Simplified);
            languages.add(Languages.Chinese_Traditional);
            languages.add(Languages.Corsican);
            languages.add(Languages.Croatian);
            languages.add(Languages.Czech);
            languages.add(Languages.Danish);
            languages.add(Languages.Dutch);
            languages.add(Languages.English);
            languages.add(Languages.Esperanto);
            languages.add(Languages.Estonian);
            languages.add(Languages.Filipino);
            languages.add(Languages.Finnish);
            languages.add(Languages.French);
            languages.add(Languages.Frisian);
            languages.add(Languages.Galician);
            languages.add(Languages.Georgian);
            languages.add(Languages.German);
            languages.add(Languages.Greek);
            languages.add(Languages.Gujarati);
            languages.add(Languages.Haitian_Creole);
            languages.add(Languages.Hausa);
            languages.add(Languages.Hawaiian);
            languages.add(Languages.Hindi);
            languages.add(Languages.Hmong);
            languages.add(Languages.Hungarian);
            languages.add(Languages.Icelandic);
            languages.add(Languages.Igbo);
            languages.add(Languages.Indonesian);
            languages.add(Languages.Irish);
            languages.add(Languages.Italian);
            languages.add(Languages.Japanese);
            languages.add(Languages.Javanese);
            languages.add(Languages.Kannada);
            languages.add(Languages.Kazakh);
            languages.add(Languages.Khmer);
            languages.add(Languages.Korean);
            languages.add(Languages.Kurdish);
            languages.add(Languages.Kyrgyz);
            languages.add(Languages.Lao);
            languages.add(Languages.Latin);
            languages.add(Languages.Latvian);
            languages.add(Languages.Lithuanian);
            languages.add(Languages.Luxembourgish);
            languages.add(Languages.Macedonian);
            languages.add(Languages.Malagasy);
            languages.add(Languages.Malay);
            languages.add(Languages.Malayalam);
            languages.add(Languages.Maltese);
            languages.add(Languages.Maori);
            languages.add(Languages.Marathi);
            languages.add(Languages.Mongolian);
            languages.add(Languages.Nepali);
            languages.add(Languages.Norwegian);
            languages.add(Languages.Pashto);
            languages.add(Languages.Persian);
            languages.add(Languages.Polish);
            languages.add(Languages.Portuguese);
            languages.add(Languages.Punjabi);
            languages.add(Languages.Romanian);
            languages.add(Languages.Russian);
            languages.add(Languages.Samoan);
            languages.add(Languages.Scots_Gaelic);
            languages.add(Languages.Serbian);
            languages.add(Languages.Sesotho);
            languages.add(Languages.Shona);
            languages.add(Languages.Sindhi);
            languages.add(Languages.Sinhala);
            languages.add(Languages.Slovak);
            languages.add(Languages.Somali);
            languages.add(Languages.Spanish);
            languages.add(Languages.Sundanese);
            languages.add(Languages.Swahili);
            languages.add(Languages.Swedish);
            languages.add(Languages.Tajik);
            languages.add(Languages.Tamil);
            languages.add(Languages.Telagu);
            languages.add(Languages.Thai);
            languages.add(Languages.Turkish);
            languages.add(Languages.Ukrainian);
            languages.add(Languages.Urdu);
            languages.add(Languages.Uzbek);
            languages.add(Languages.Viatnamese);
            languages.add(Languages.Welsh);
            languages.add(Languages.Xhosa);
            languages.add(Languages.Yoruba);
            languages.add(Languages.Zulu);
            for (String langs : languages) {
                //create the directory for that languages resources
                CreateDir(path + "values-" + langs);
                System.out.println("Creating folder values-" + langs);
                File file = new File(stringFilePath);
                DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder();
                Document doc = dBuilder.parse(file);
                NodeList list = doc.getDocumentElement().getElementsByTagName("string");
                //create a list to hold the string properties
                List<StringProperty> props = new ArrayList<>();
                for (int i = 0; i < list.getLength(); i++) {
                    Node node = list.item(i);
                    String name = node.getAttributes().getNamedItem("name").getNodeValue();
                    String value = node.getFirstChild().getNodeValue();
                    Translater trans = new Translater();
                    System.out.println("Translating the string " +value);
                    value =  trans.translate(currLang,langs,value);
                  //sleep for a second to prevent google throttling
                    System.out.println("Translation is " + value);
                    System.out.println("Sleeping for 1 second to avoid google throttling");
                    Thread.sleep(1000);
                    System.out.println("Finished Translating");
                    StringProperty prop = new StringProperty(name, value);
                    props.add(prop);
                }
                //now write the new strings file
                PrintStringsFile(props, new File(path + "values-" + langs + "/strings.xml"));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void CreateDir(String path) {
        File file = new File(path);
        //only create it if the file does not exist
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private static void PrintStringsFile(List<StringProperty> strings, File file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            writer.println("<resources>");
            for (StringProperty prop : strings) {
                writer.print("<string name=\"");
                writer.print(prop.getName());
                writer.print("\">");
                writer.print(prop.getValue());
                writer.println("</string>");
            }
            writer.println("</resources>");
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
