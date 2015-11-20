package phishDetect;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author vishwas
 */

import data.Url;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URL_Utilities {
    // static final String[] phish_list = com.vish.phishDetect.data.phish_list;
    static final String[] tld = {"ac", "academy", "accountants", "actor", "ad", "ae", "aero", "af", "ag", "agency", "ai", "airforce", "al", "am", "an", "ao", "aq", "ar", "archi", "army", "arpa", "as", "asia", "associates", "at", "attorney", "au", "audio", "autos", "aw", "ax", "axa", "az", "ba", "bar", "bargains", "bayern", "bb", "bd", "be", "beer", "berlin", "best", "bf", "bg", "bh", "bi", "bid", "bike", "bio", "biz", "bj", "black", "blackfriday", "blue", "bm", "bn", "bo", "boutique", "br", "brussels", "bs", "bt", "build", "builders", "buzz", "bv", "bw", "by", "bz", "bzh", "ca", "cab", "camera", "camp", "capetown", "capital", "cards", "care", "career", "careers", "cash", "cat", "catering", "cc", "cd", "center", "ceo", "cf", "cg", "ch", "cheap", "christmas", "church", "ci", "citic", "ck", "cl", "claims", "cleaning", "clinic", "clothing", "club", "cm", "cn", "co", "codes", "coffee", "college", "cologne", "com", "community", "company", "computer", "condos", "construction", "consulting", "contractors", "cooking", "cool", "coop", "country", "cr", "credit", "creditcard", "cruises", "cu", "cv", "cw", "cx", "cy", "cz", "dance", "dating", "de", "degree", "democrat", "dental", "dentist", "desi", "diamonds", "digital", "directory", "discount", "dj", "dk", "dm", "dnp", "do", "domains", "durban", "dz", "ec", "edu", "education", "ee", "eg", "email", "engineer", "engineering", "enterprises", "equipment", "er", "es", "estate", "et", "eu", "eus", "events", "exchange", "expert", "exposed", "fail", "farm", "feedback", "fi", "finance", "financial", "fish", "fishing", "fitness", "fj", "fk", "flights", "florist", "fm", "fo", "foo", "foundation", "fr", "frogans", "fund", "furniture", "futbol", "ga", "gal", "gallery", "gb", "gd", "ge", "gf", "gg", "gh", "gi", "gift", "gives", "gl", "glass", "global", "globo", "gm", "gmo", "gn", "gop", "gov", "gp", "gq", "gr", "graphics", "gratis", "green", "gripe", "gs", "gt", "gu", "guide", "guitars", "guru", "gw", "gy", "hamburg", "haus", "hiphop", "hiv", "hk", "hm", "hn", "holdings", "holiday", "homes", "horse", "host", "house", "hr", "ht", "hu", "id", "ie", "il", "im", "immobilien", "in", "industries", "info", "ink", "institute", "insure", "int", "international", "investments", "io", "iq", "ir", "is", "it", "je", "jetzt", "jm", "jo", "jobs", "joburg", "jp", "juegos", "kaufen", "ke", "kg", "kh", "ki", "kim", "kitchen", "kiwi", "km", "kn", "koeln", "kp", "kr", "kred", "kw", "ky", "kz", "la", "land", "lawyer", "lb", "lc", "lease", "li", "life", "lighting", "limited", "limo", "link", "lk", "loans", "london", "lotto", "lr", "ls", "lt", "lu", "luxe", "luxury", "lv", "ly", "ma", "maison", "management", "mango", "market", "marketing", "mc", "md", "me", "media", "meet", "menu", "mg", "mh", "miami", "mil", "mk", "ml", "mm", "mn", "mo", "mobi", "moda", "moe", "monash", "mortgage", "moscow", "motorcycles", "mp", "mq", "mr", "ms", "mt", "mu", "museum", "mv", "mw", "mx", "my", "mz", "na", "nagoya", "name", "navy", "nc", "ne", "net", "neustar", "nf", "ng", "nhk", "ni", "ninja", "nl", "no", "np", "nr", "nu", "nyc", "nz", "okinawa", "om", "onl", "org", "organic", "ovh", "pa", "paris", "partners", "parts", "pe", "pf", "pg", "ph", "photo", "photography", "photos", "physio", "pics", "pictures", "pink", "pk", "pl", "plumbing", "pm", "pn", "post", "pr", "press", "pro", "productions", "properties", "ps", "pt", "pub", "pw", "py", "qa", "qpon", "quebec", "re", "recipes", "red", "rehab", "reise", "reisen", "ren", "rentals", "repair", "report", "republican", "rest", "reviews", "rich", "rio", "ro", "rocks", "rodeo", "rs", "ru", "ruhr", "rw", "ryukyu", "sa", "saarland", "sb", "sc", "schule", "scot", "sd", "se", "services", "sexy", "sg", "sh", "shiksha", "shoes", "si", "singles", "sj", "sk", "sl", "sm", "sn", "so", "social", "software", "sohu", "solar", "solutions", "soy", "space", "sr", "st", "su", "supplies", "supply", "support", "surf", "surgery", "sv", "sx", "sy", "systems", "sz", "tattoo", "tax", "tc", "td", "technology", "tel", "tf", "tg", "th", "tienda", "tips", "tirol", "tj", "tk", "tl", "tm", "tn", "to", "today", "tokyo", "tools", "town", "toys", "tp", "tr", "trade", "training", "travel", "tt", "tv", "tw", "tz", "ua", "ug", "uk", "university", "uno", "us", "uy", "uz", "va", "vacations", "vc", "ve", "vegas", "ventures", "versicherung", "vet", "vg", "vi", "viajes", "villas", "vision", "vlaanderen", "vn", "vodka", "vote", "voting", "voto", "voyage", "vu", "wang", "watch", "webcam", "website", "wed", "wf", "wien", "wiki", "works", "ws", "wtc", "wtf", "xn--3bst00m", "xn--3ds443g", "xn--3e0b707e", "xn--45brj9c", "xn--4gbrim", "xn--55qw42g", "xn--55qx5d", "xn--6frz82g", "xn--6qq986b3xl", "xn--80adxhks", "xn--80ao21a", "xn--80asehdb", "xn--80aswg", "xn--90a3ac", "xn--c1avg", "xn--cg4bki", "xn--clchc0ea0b2g2a9gcd", "xn--czr694b", "xn--czru2d", "xn--d1acj3b", "xn--fiq228c5hs", "xn--fiq64b", "xn--fiqs8s", "xn--fiqz9s", "xn--fpcrj9c3d", "xn--fzc2c9e2c", "xn--gecrj9c", "xn--h2brj9c", "xn--i1b6b1a6a2e", "xn--io0a7i", "xn--j1amh", "xn--j6w193g", "xn--kprw13d", "xn--kpry57d", "xn--kput3i", "xn--l1acc", "xn--lgbbat1ad8j", "xn--mgb9awbf", "xn--mgba3a4f16a", "xn--mgbaam7a8h", "xn--mgbab2bd", "xn--mgbayh7gpa", "xn--mgbbh1a71e", "xn--mgbc0a9azcg", "xn--mgberp4a5d4ar", "xn--mgbx4cd0ab", "xn--ngbc5azd", "xn--nqv7f", "xn--nqv7fs00ema", "xn--o3cw4h", "xn--ogbpf8fl", "xn--p1ai", "xn--pgbs0dh", "xn--q9jyb4c", "xn--rhqv96g", "xn--s9brj9c", "xn--ses554g", "xn--unup4y", "xn--wgbh1c", "xn--wgbl6a", "xn--xkc2al3hye2a", "xn--xkc2dl3a5ee0h", "xn--yfro4i67o", "xn--ygbi2ammx", "xn--zfr164b", "xxx", "xyz", "yachts", "ye", "yokohama", "yt", "za", "zm", "zone", "zw"};
    Url url;

    private final static Logger logger = Logger.getLogger(URL_Utilities.class);
    public URL_Utilities(Url url) {
        this.url= url;
    }

    double checkURL() {

        int dots = (numberOfDots()); //Number of dots in URL


        int suspiciousURL = suspiciousURL();
        //logger.debug("suspiciousUrl" + suspiciousURL);

        int has_IP = 0;
        if (hasIP()) {
            has_IP = 1;
            //logger.debug("hasIP:" + 1);

        }
        int has_embedded_domain = 0;
        if (hasEmbeddedDomain()) {
            //logger.debug("hasEmbeddedDomain" + 1);
            has_embedded_domain = 1;

        }
        int out_of_podition_tld = 0;
        if (outOfPositionTLD()) {
            out_of_podition_tld = 1;
            //logger.debug("outOfPositionTLD" + 1);

        }
        int senstive_words = senstiveWords();
        double ans = dots * 0.6703 + suspiciousURL * 0.5472 + has_IP * 1.978 + has_embedded_domain * 1.4154 + out_of_podition_tld * 2.0249 + senstive_words * 1.0106;
        //logger.debug ("senstiveWords" + senstive_words);
        logger.debug(dots + "~" + suspiciousURL + "~" + has_IP + "~" + has_embedded_domain + "~" + out_of_podition_tld + "~" +senstive_words +"~");
        return ans;
    }




    int numberOfDots() {
        return (new StringTokenizer(url.getUrl(), ".")).countTokens() - 1;
    }

    int suspiciousURL() {
        int ctr = 0;
        String _url = url.getUrl();
        for (int i = 0; i < _url.length(); i++)                        //Suspicious URL
        {
            if (_url.charAt(i) == '@') {
                ctr += 2;
            }
            if (_url.charAt(i) == '-')
                ctr++;
        }
        return ctr;
    }

    boolean hasIP() {
        String _url = url.getUrl();
        Pattern p = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
        Matcher m = p.matcher(_url);
        return m.find();
    }

    int senstiveWords() {
        String _url = url.getUrl();
        String[] words = {"secure", "account", "webscr", "login", "ebayisapi", "signin", "banking", "confirm"};
        int ctr = 0;
        for (String word : words) {
            String patternString1 = "(" + word + ")";
            Pattern pattern = Pattern.compile(patternString1);
            Matcher matcher = pattern.matcher(_url);
            while (matcher.find()) {
                ctr++;
            }
        }
        return ctr;
    }

    boolean outOfPositionTLD() {
        String _url = url.getUrl();
        boolean flag = false;
        try {
            URL url = new URL(_url);
            String[] domainNameParts = url.getHost().split("\\.");
            for (String part : domainNameParts) {
                if (flag) {
                    if (Arrays.binarySearch(tld, part) < 0) {
                        System.out.println(_url + "\n" + "outOfPositionTLD");
                        return true;
                    }
                } else if (Arrays.binarySearch(tld, part) >= 0) {
                    flag = true;
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return false;
    }

    boolean hasEmbeddedDomain() {
        String _url = url.getUrl();
        URL url;
        try {
            url = new URL(_url);
            String p = url.getPath();
            String patternString1 = "[A-Za-z0-9-]+(\\.[A-Za-z0-9_]{2,})(\\.[A-Za-z_]{2,})+";
            Pattern pattern = Pattern.compile(patternString1);
            Matcher matcher = pattern.matcher(p);
            if (matcher.find()) {
                //System.out.println(p+"\n"+matcher.group(0)+"\n"+"domain");
                return true;
            }
        } catch (MalformedURLException e) {
        }
        return false;
    }
}