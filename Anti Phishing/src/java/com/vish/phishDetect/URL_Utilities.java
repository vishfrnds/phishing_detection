package com.vish.phishDetect;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vishwas
 */

import com.vish.precompute.Data;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class URL_Utilities 
{    
   // static final String[] phish_list = com.vish.phishDetect.Data.phish_list;
    static final String[] tld = {"ac",  "academy",  "accountants",  "actor",  "ad",  "ae",  "aero",  "af",  "ag",  "agency",  "ai",  "airforce",  "al",  "am",  "an",  "ao",  "aq",  "ar",  "archi",  "army",  "arpa",  "as",  "asia",  "associates",  "at",  "attorney",  "au",  "audio",  "autos",  "aw",  "ax",  "axa",  "az",  "ba",  "bar",  "bargains",  "bayern",  "bb",  "bd",  "be",  "beer",  "berlin",  "best",  "bf",  "bg",  "bh",  "bi",  "bid",  "bike",  "bio",  "biz",  "bj",  "black",  "blackfriday",  "blue",  "bm",  "bn",  "bo",  "boutique",  "br",  "brussels",  "bs",  "bt",  "build",  "builders",  "buzz",  "bv",  "bw",  "by",  "bz",  "bzh",  "ca",  "cab",  "camera",  "camp",  "capetown",  "capital",  "cards",  "care",  "career",  "careers",  "cash",  "cat",  "catering",  "cc",  "cd",  "center",  "ceo",  "cf",  "cg",  "ch",  "cheap",  "christmas",  "church",  "ci",  "citic",  "ck",  "cl",  "claims",  "cleaning",  "clinic",  "clothing",  "club",  "cm",  "cn",  "co",  "codes",  "coffee",  "college",  "cologne",  "com",  "community",  "company",  "computer",  "condos",  "construction",  "consulting",  "contractors",  "cooking",  "cool",  "coop",  "country",  "cr",  "credit",  "creditcard",  "cruises",  "cu",  "cv",  "cw",  "cx",  "cy",  "cz",  "dance",  "dating",  "de",  "degree",  "democrat",  "dental",  "dentist",  "desi",  "diamonds",  "digital",  "directory",  "discount",  "dj",  "dk",  "dm",  "dnp",  "do",  "domains",  "durban",  "dz",  "ec",  "edu",  "education",  "ee",  "eg",  "email",  "engineer",  "engineering",  "enterprises",  "equipment",  "er",  "es",  "estate",  "et",  "eu",  "eus",  "events",  "exchange",  "expert",  "exposed",  "fail",  "farm",  "feedback",  "fi",  "finance",  "financial",  "fish",  "fishing",  "fitness",  "fj",  "fk",  "flights",  "florist",  "fm",  "fo",  "foo",  "foundation",  "fr",  "frogans",  "fund",  "furniture",  "futbol",  "ga",  "gal",  "gallery",  "gb",  "gd",  "ge",  "gf",  "gg",  "gh",  "gi",  "gift",  "gives",  "gl",  "glass",  "global",  "globo",  "gm",  "gmo",  "gn",  "gop",  "gov",  "gp",  "gq",  "gr",  "graphics",  "gratis",  "green",  "gripe",  "gs",  "gt",  "gu",  "guide",  "guitars",  "guru",  "gw",  "gy",  "hamburg",  "haus",  "hiphop",  "hiv",  "hk",  "hm",  "hn",  "holdings",  "holiday",  "homes",  "horse",  "host",  "house",  "hr",  "ht",  "hu",  "id",  "ie",  "il",  "im",  "immobilien",  "in",  "industries",  "info",  "ink",  "institute",  "insure",  "int",  "international",  "investments",  "io",  "iq",  "ir",  "is",  "it",  "je",  "jetzt",  "jm",  "jo",  "jobs",  "joburg",  "jp",  "juegos",  "kaufen",  "ke",  "kg",  "kh",  "ki",  "kim",  "kitchen",  "kiwi",  "km",  "kn",  "koeln",  "kp",  "kr",  "kred",  "kw",  "ky",  "kz",  "la",  "land",  "lawyer",  "lb",  "lc",  "lease",  "li",  "life",  "lighting",  "limited",  "limo",  "link",  "lk",  "loans",  "london",  "lotto",  "lr",  "ls",  "lt",  "lu",  "luxe",  "luxury",  "lv",  "ly",  "ma",  "maison",  "management",  "mango",  "market",  "marketing",  "mc",  "md",  "me",  "media",  "meet",  "menu",  "mg",  "mh",  "miami",  "mil",  "mk",  "ml",  "mm",  "mn",  "mo",  "mobi",  "moda",  "moe",  "monash",  "mortgage",  "moscow",  "motorcycles",  "mp",  "mq",  "mr",  "ms",  "mt",  "mu",  "museum",  "mv",  "mw",  "mx",  "my",  "mz",  "na",  "nagoya",  "name",  "navy",  "nc",  "ne",  "net",  "neustar",  "nf",  "ng",  "nhk",  "ni",  "ninja",  "nl",  "no",  "np",  "nr",  "nu",  "nyc",  "nz",  "okinawa",  "om",  "onl",  "org",  "organic",  "ovh",  "pa",  "paris",  "partners",  "parts",  "pe",  "pf",  "pg",  "ph",  "photo",  "photography",  "photos",  "physio",  "pics",  "pictures",  "pink",  "pk",  "pl",  "plumbing",  "pm",  "pn",  "post",  "pr",  "press",  "pro",  "productions",  "properties",  "ps",  "pt",  "pub",  "pw",  "py",  "qa",  "qpon",  "quebec",  "re",  "recipes",  "red",  "rehab",  "reise",  "reisen",  "ren",  "rentals",  "repair",  "report",  "republican",  "rest",  "reviews",  "rich",  "rio",  "ro",  "rocks",  "rodeo",  "rs",  "ru",  "ruhr",  "rw",  "ryukyu",  "sa",  "saarland",  "sb",  "sc",  "schule",  "scot",  "sd",  "se",  "services",  "sexy",  "sg",  "sh",  "shiksha",  "shoes",  "si",  "singles",  "sj",  "sk",  "sl",  "sm",  "sn",  "so",  "social",  "software",  "sohu",  "solar",  "solutions",  "soy",  "space",  "sr",  "st",  "su",  "supplies",  "supply",  "support",  "surf",  "surgery",  "sv",  "sx",  "sy",  "systems",  "sz",  "tattoo",  "tax",  "tc",  "td",  "technology",  "tel",  "tf",  "tg",  "th",  "tienda",  "tips",  "tirol",  "tj",  "tk",  "tl",  "tm",  "tn",  "to",  "today",  "tokyo",  "tools",  "town",  "toys",  "tp",  "tr",  "trade",  "training",  "travel",  "tt",  "tv",  "tw",  "tz",  "ua",  "ug",  "uk",  "university",  "uno",  "us",  "uy",  "uz",  "va",  "vacations",  "vc",  "ve",  "vegas",  "ventures",  "versicherung",  "vet",  "vg",  "vi",  "viajes",  "villas",  "vision",  "vlaanderen",  "vn",  "vodka",  "vote",  "voting",  "voto",  "voyage",  "vu",  "wang",  "watch",  "webcam",  "website",  "wed",  "wf",  "wien",  "wiki",  "works",  "ws",  "wtc",  "wtf",  "xn--3bst00m",  "xn--3ds443g",  "xn--3e0b707e",  "xn--45brj9c",  "xn--4gbrim",  "xn--55qw42g",  "xn--55qx5d",  "xn--6frz82g",  "xn--6qq986b3xl",  "xn--80adxhks",  "xn--80ao21a",  "xn--80asehdb",  "xn--80aswg",  "xn--90a3ac",  "xn--c1avg",  "xn--cg4bki",  "xn--clchc0ea0b2g2a9gcd",  "xn--czr694b",  "xn--czru2d",  "xn--d1acj3b",  "xn--fiq228c5hs",  "xn--fiq64b",  "xn--fiqs8s",  "xn--fiqz9s",  "xn--fpcrj9c3d",  "xn--fzc2c9e2c",  "xn--gecrj9c",  "xn--h2brj9c",  "xn--i1b6b1a6a2e",  "xn--io0a7i",  "xn--j1amh",  "xn--j6w193g",  "xn--kprw13d",  "xn--kpry57d",  "xn--kput3i",  "xn--l1acc",  "xn--lgbbat1ad8j",  "xn--mgb9awbf",  "xn--mgba3a4f16a",  "xn--mgbaam7a8h",  "xn--mgbab2bd",  "xn--mgbayh7gpa",  "xn--mgbbh1a71e",  "xn--mgbc0a9azcg",  "xn--mgberp4a5d4ar",  "xn--mgbx4cd0ab",  "xn--ngbc5azd",  "xn--nqv7f",  "xn--nqv7fs00ema",  "xn--o3cw4h",  "xn--ogbpf8fl",  "xn--p1ai",  "xn--pgbs0dh",  "xn--q9jyb4c",  "xn--rhqv96g",  "xn--s9brj9c",  "xn--ses554g",  "xn--unup4y",  "xn--wgbh1c",  "xn--wgbl6a",  "xn--xkc2al3hye2a",  "xn--xkc2dl3a5ee0h",  "xn--yfro4i67o",  "xn--ygbi2ammx",  "xn--zfr164b",  "xxx",  "xyz",  "yachts",  "ye",  "yokohama",  "yt",  "za",  "zm",  "zone",  "zw"};
    static int checkURL(String _url)
    {
        int ans = 0;
        int dots= (numberOfDots(_url)); //Number of dots in URL
        if(dots >= 3)
            ans += dots * 5;
        ans += suspiciousURL(_url)*2;
        if(hasIP(_url))
        {
            ans += 80;
            //System.out.println("IP");
        }
        if(hasEmbeddedDomain(_url))
            ans += 80;
        if(outOfPositionTLD(_url))
            ans += 40;
        ans += senstiveWords(_url)*10;
        return ans;
    }
    
    public static int perMatch(String _url)// to find percentage match with given list of URL
    {
        int l = _url.length(), high = Data.phish_list.size() - 1, low = 0, mid, i, flag, ans = 0, maxi = 0;
        while(high >= low)
        {
            flag = 0;
            mid = (high + low) / 2;
            //System.out.println(phish_list[mid]+" "+high+" "+" "+mid +" "+low);
            int len = Data.phish_list.get(mid).length();
            for(i = 0; i < Math.min(l, len); i++)
            {
                if(_url.charAt(i) != Data.phish_list.get(mid).charAt(i))
                {
                    flag = 1;
                    break;
                }
            }
            if(flag == 1)
            {
                    if(i > maxi)
                    {
                        ans = mid;
                        maxi = i;
                    }
                    if(_url.charAt(i) > Data.phish_list.get(mid).charAt(i))
                        low = mid + 1;
                    else
                        high = mid - 1;                        
            } 
            else
            {
                if(i > maxi)
                    {
                        ans = mid;
                        maxi = i;
                    }
                if(i == len)                    
                    low = mid + 1;
                else
                    high = mid - 1;
            }
        }
        //System.out.println(phish_list[ans]+"  "+_url +maxi+" "+Math.min(l, phish_list[ans].length()));
        int per = (int)(maxi*100.0/Data.phish_list.get(ans).length());
        return per;
    }

    
    static int numberOfDots(String _url)
    {
        return (new StringTokenizer(_url, ".")).countTokens() - 1;
    }
    static int suspiciousURL(String _url)
    {
        int ctr = 0;
        for(int i = 0; i < _url.length(); i++)                        //Suspicious URL
        {
            if(_url.charAt(i) == '@')
            {
                ctr += 2;
            }
            if(_url.charAt(i) == '-')
                ctr++;
        }
        return ctr;
    }
    static boolean hasIP(String _url) 
    {
 
        Pattern p = Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
        Matcher m = p.matcher(_url);
        return m.find();
    }

    static int senstiveWords(String _url) 
    {
        String[] words = {"secure", "account", "webscr", "login", "ebayisapi", "signin", "banking", "confirm"};
        int ctr = 0;
        for(String word : words)
        {
            String patternString1 = "("+word+")";
            Pattern pattern = Pattern.compile(patternString1);
            Matcher matcher = pattern.matcher(_url);
            while(matcher.find()) 
            {
                ctr++;
            }
        }
        return ctr;
    }
    static boolean outOfPositionTLD(String _url) 
    {
        boolean flag = false;
        try
        {
            URL url = new URL(_url);
            String[] domainNameParts = url.getHost().split("\\.");
            for(String part : domainNameParts )
            {
                if(flag)
                {
                    if(Arrays.binarySearch(tld,part) < 0)
                    {
                        System.out.println(_url + "\n" + "outOfPositionTLD");
                        return true;
                    }
                }
                else if(Arrays.binarySearch(tld,part) >=0 )
                {
                    flag = true;
                }
                
            }
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
       
        return false;
    }
    
    static boolean hasEmbeddedDomain(String _url)
    {
        URL url;
        try
        {
            url = new URL(_url);
            String p = url.getPath();
            String patternString1 = "[A-Za-z0-9-]+(\\.[A-Za-z0-9_]{2,})(\\.[A-Za-z_]{2,})+";
            Pattern pattern = Pattern.compile(patternString1);
            Matcher matcher = pattern.matcher(p);
            if(matcher.find()) 
            {
                //System.out.println(p+"\n"+matcher.group(0)+"\n"+"domain");
                return true;
            }
        }
        catch(MalformedURLException e)
        {
            url = null;
        }
        return false;
    }   
}