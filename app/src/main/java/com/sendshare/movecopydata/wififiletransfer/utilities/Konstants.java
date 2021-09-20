package com.sendshare.movecopydata.wififiletransfer.utilities;

import android.content.Context;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.io.File;

public class Konstants {
    public static final int SERVER_PORT = 3356;
    public static final int HELLO_SERVER_PORT = 1234;
    public static final String DEVICE_IP = "DEVICE_IP";
    public static final String DEVICE_RECEIVER_PORT = "DEVICE_RECEIVER_PORT";

  public static final int INTERNAL_STORAGE = 0;
  public static final int EXTERNAL_STORAGE = 1;
  public static final int ITEM_TO_SCROLL = 20;
  public static final String APP_FOLDER = "Share Blue";
  public static Uri myUri;

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public static String getDEFAULT_DIRECTORY_TO_SAVE(Context context) {
    return ContextCompat.getExternalFilesDirs(context, null)[0] +
            File.separator + APP_FOLDER;
  }
 /*
  public static String getStringCodeFromInt(int code) {
     switch (code) {
       case 0 : return "ab";
       case 1 : return "ac";
       case 2 : return "ad";
       case 3 : return "ae";
       case 4 : return "af";
       case 5 : return "ag";
       case 6 : return "ah";
       case 7 : return "ai";
       case 8 : return "aj";
       case 9 : return "ak";
       case 10 : return "al";
       case 11 : return "am";
       case 12 : return "an";
       case 13 : return "ao";
       case 14 : return "ap";
       case 15 : return "aq";
       case 16 : return "ar";
       case 17 : return "as";
       case 18 : return "at";
       case 19 : return "au";
       case 20 : return "av";
       case 21 : return "aw";
       case 22 : return "ax";
       case 23 : return "bc";
       case 24 : return "bd";
       case 25 : return "be";
       case 26 : return "bf";
       case 27 : return "bg";
       case 28 : return "bh";
       case 29 : return "bi";
       case 30 : return "bj";
       case 31 : return "bk";
       case 32 : return "bl";
       case 33 : return "bm";
       case 34 : return "bn";
       case 35 : return "bo";
       case 36 : return "bp";
       case 37 : return "bq";
       case 38 : return "br";
       case 39 : return "bs";
       case 40 : return "bt";
       case 41 : return "bu";
       case 42 : return "bv";
       case 43 : return "bw";
       case 44 : return "bx";
       case 45 : return "cd";
       case 46 : return "ce";
       case 47 : return "cf";
       case 48 : return "cg";
       case 49 : return "ch";
       case 50 : return "ci";
       case 51 : return "cj";
       case 52 : return "ck";
       case 53 : return "cl";
       case 54 : return "cm";
       case 55 : return "cn";
       case 56 : return "co";
       case 57 : return "cp";
       case 58 : return "cq";
       case 59 : return "cr";
       case 60 : return "cs";
       case 61 : return "ct";
       case 62 : return "cu";
       case 63 : return "cv";
       case 64 : return "cw";
       case 65 : return "cx";
       case 66 : return "de";
       case 67 : return "df";
       case 68 : return "dg";
       case 69 : return "dh";
       case 70 : return "di";
       case 71 : return "dj";
       case 72 : return "dk";
       case 73 : return "dl";
       case 74 : return "dm";
       case 75 : return "dn";
       case 76 : return "do";
       case 77 : return "dp";
       case 78 : return "dq";
       case 79 : return "dr";
       case 80 : return "ds";
       case 81 : return "dt";
       case 82 : return "du";
       case 83 : return "dv";
       case 84 : return "dw";
       case 85 : return "dx";
       case 86 : return "ef";
       case 87 : return "eg";
       case 88 : return "eh";
       case 89 : return "ei";
       case 90 : return "ej";
       case 91 : return "ek";
       case 92 : return "el";
       case 93 : return "em";
       case 94 : return "en";
       case 95 : return "eo";
       case 96 : return "ep";
       case 97 : return "eq";
       case 98 : return "er";
       case 99 : return "es";
       case 100 : return "et";
       case 101 : return "eu";
       case 102 : return "ev";
       case 103 : return "ew";
       case 104 : return "ex";
       case 105 : return "fg";
       case 106 : return "fh";
       case 107 : return "fi";
       case 108 : return "fj";
       case 109 : return "fk";
       case 110 : return "fl";
       case 111 : return "fm";
       case 112 : return "fn";
       case 113 : return "fo";
       case 114 : return "fp";
       case 115 : return "fq";
       case 116 : return "fr";
       case 117 : return "fs";
       case 118 : return "ft";
       case 119 : return "fu";
       case 120 : return "fv";
       case 121 : return "fw";
       case 122 : return "fx";
       case 123 : return "gh";
       case 124 : return "gi";
       case 125 : return "gj";
       case 126 : return "gk";
       case 127 : return "gl";
       case 128 : return "gm";
       case 129 : return "gn";
       case 130 : return "go";
       case 131 : return "gp";
       case 132 : return "gq";
       case 133 : return "gr";
       case 134 : return "gs";
       case 135 : return "gt";
       case 136 : return "gu";
       case 137 : return "gv";
       case 138 : return "gw";
       case 139 : return "gx";
       case 140 : return "hi";
       case 141 : return "hj";
       case 142 : return "hk";
       case 143 : return "hl";
       case 144 : return "hm";
       case 145 : return "hn";
       case 146 : return "ho";
       case 147 : return "hp";
       case 148 : return "hq";
       case 149 : return "hr";
       case 150 : return "hs";
       case 151 : return "ht";
       case 152 : return "hu";
       case 153 : return "hv";
       case 154 : return "hw";
       case 155 : return "hx";
       case 156 : return "ij";
       case 157 : return "ik";
       case 158 : return "il";
       case 159 : return "im";
       case 160 : return "in";
       case 161 : return "io";
       case 162 : return "ip";
       case 163 : return "iq";
       case 164 : return "ir";
       case 165 : return "is";
       case 166 : return "it";
       case 167 : return "iu";
       case 168 : return "iv";
       case 169 : return "iw";
       case 170 : return "ix";
       case 171 : return "jk";
       case 172 : return "jl";
       case 173 : return "jm";
       case 174 : return "jn";
       case 175 : return "jo";
       case 176 : return "jp";
       case 177 : return "jq";
       case 178 : return "jr";
       case 179 : return "js";
       case 180 : return "jt";
       case 181 : return "ju";
       case 182 : return "jv";
       case 183 : return "jw";
       case 184 : return "jx";
       case 185 : return "kl";
       case 186 : return "km";
       case 187 : return "kn";
       case 188 : return "ko";
       case 189 : return "kp";
       case 190 : return "kq";
       case 191 : return "kr";
       case 192 : return "ks";
       case 193 : return "kt";
       case 194 : return "ku";
       case 195 : return "kv";
       case 196 : return "kw";
       case 197 : return "kx";
       case 198 : return "lm";
       case 199 : return "ln";
       case 200 : return "lo";
       case 201 : return "lp";
       case 202 : return "lq";
       case 203 : return "lr";
       case 204 : return "ls";
       case 205 : return "lt";
       case 206 : return "lu";
       case 207 : return "lv";
       case 208 : return "lw";
       case 209 : return "lx";
       case 210 : return "mn";
       case 211 : return "mo";
       case 212 : return "mp";
       case 213 : return "mq";
       case 214 : return "mr";
       case 215 : return "ms";
       case 216 : return "mt";
       case 217 : return "mu";
       case 218 : return "mv";
       case 219 : return "mw";
       case 220 : return "mx";
       case 221 : return "no";
       case 222 : return "np";
       case 223 : return "nq";
       case 224 : return "nr";
       case 225 : return "ns";
       case 226 : return "nt";
       case 227 : return "nu";
       case 228 : return "nv";
       case 229 : return "nw";
       case 230 : return "nx";
       case 231 : return "op";
       case 232 : return "oq";
       case 233 : return "or";
       case 234 : return "os";
       case 235 : return "ot";
       case 236 : return "ou";
       case 237 : return "ov";
       case 238 : return "ow";
       case 239 : return "ox";
       case 240 : return "pq";
       case 241 : return "pr";
       case 242 : return "ps";
       case 243 : return "pt";
       case 244 : return "pu";
       case 245 : return "pv";
       case 246 : return "pw";
       case 247 : return "px";
       case 248 : return "qr";
       case 249 : return "qs";
       case 250 : return "qt";
       case 251 : return "qu";
       case 252 : return "qv";
       case 253 : return "qw";
       case 254 : return "qx";
       case 255 : return "rs";
       default:return null;
     }
  }
   static int getIntCodeFromString(String code) {
    switch (code.toLowerCase()) {
      case "ab" : return 0;
      case "ac" : return 1;
      case "ad" : return 2;
      case "ae" : return 3;
      case "af" : return 4;
      case "ag" : return 5;
      case "ah" : return 6;
      case "ai" : return 7;
      case "aj" : return 8;
      case "ak" : return 9;
      case "al" : return 10;
      case "am" : return 11;
      case "an" : return 12;
      case "ao" : return 13;
      case "ap" : return 14;
      case "aq" : return 15;
      case "ar" : return 16;
      case "as" : return 17;
      case "at" : return 18;
      case "au" : return 19;
      case "av" : return 20;
      case "aw" : return 21;
      case "ax" : return 22;
      case "bc" : return 23;
      case "bd" : return 24;
      case "be" : return 25;
      case "bf" : return 26;
      case "bg" : return 27;
      case "bh" : return 28;
      case "bi" : return 29;
      case "bj" : return 30;
      case "bk" : return 31;
      case "bl" : return 32;
      case "bm" : return 33;
      case "bn" : return 34;
      case "bo" : return 35;
      case "bp" : return 36;
      case "bq" : return 37;
      case "br" : return 38;
      case "bs" : return 39;
      case "bt" : return 40;
      case "bu" : return 41;
      case "bv" : return 42;
      case "bw" : return 43;
      case "bx" : return 44;
      case "cd" : return 45;
      case "ce" : return 46;
      case "cf" : return 47;
      case "cg" : return 48;
      case "ch" : return 49;
      case "ci" : return 50;
      case "cj" : return 51;
      case "ck" : return 52;
      case "cl" : return 53;
      case "cm" : return 54;
      case "cn" : return 55;
      case "co" : return 56;
      case "cp" : return 57;
      case "cq" : return 58;
      case "cr" : return 59;
      case "cs" : return 60;
      case "ct" : return 61;
      case "cu" : return 62;
      case "cv" : return 63;
      case "cw" : return 64;
      case "cx" : return 65;
      case "de" : return 66;
      case "df" : return 67;
      case "dg" : return 68;
      case "dh" : return 69;
      case "di" : return 70;
      case "dj" : return 71;
      case "dk" : return 72;
      case "dl" : return 73;
      case "dm" : return 74;
      case "dn" : return 75;
      case "do" : return 76;
      case "dp" : return 77;
      case "dq" : return 78;
      case "dr" : return 79;
      case "ds" : return 80;
      case "dt" : return 81;
      case "du" : return 82;
      case "dv" : return 83;
      case "dw" : return 84;
      case "dx" : return 85;
      case "ef" : return 86;
      case "eg" : return 87;
      case "eh" : return 88;
      case "ei" : return 89;
      case "ej" : return 90;
      case "ek" : return 91;
      case "el" : return 92;
      case "em" : return 93;
      case "en" : return 94;
      case "eo" : return 95;
      case "ep" : return 96;
      case "eq" : return 97;
      case "er" : return 98;
      case "es" : return 99;
      case "et" : return 100;
      case "eu" : return 101;
      case "ev" : return 102;
      case "ew" : return 103;
      case "ex" : return 104;
      case "fg" : return 105;
      case "fh" : return 106;
      case "fi" : return 107;
      case "fj" : return 108;
      case "fk" : return 109;
      case "fl" : return 110;
      case "fm" : return 111;
      case "fn" : return 112;
      case "fo" : return 113;
      case "fp" : return 114;
      case "fq" : return 115;
      case "fr" : return 116;
      case "fs" : return 117;
      case "ft" : return 118;
      case "fu" : return 119;
      case "fv" : return 120;
      case "fw" : return 121;
      case "fx" : return 122;
      case "gh" : return 123;
      case "gi" : return 124;
      case "gj" : return 125;
      case "gk" : return 126;
      case "gl" : return 127;
      case "gm" : return 128;
      case "gn" : return 129;
      case "go" : return 130;
      case "gp" : return 131;
      case "gq" : return 132;
      case "gr" : return 133;
      case "gs" : return 134;
      case "gt" : return 135;
      case "gu" : return 136;
      case "gv" : return 137;
      case "gw" : return 138;
      case "gx" : return 139;
      case "hi" : return 140;
      case "hj" : return 141;
      case "hk" : return 142;
      case "hl" : return 143;
      case "hm" : return 144;
      case "hn" : return 145;
      case "ho" : return 146;
      case "hp" : return 147;
      case "hq" : return 148;
      case "hr" : return 149;
      case "hs" : return 150;
      case "ht" : return 151;
      case "hu" : return 152;
      case "hv" : return 153;
      case "hw" : return 154;
      case "hx" : return 155;
      case "ij" : return 156;
      case "ik" : return 157;
      case "il" : return 158;
      case "im" : return 159;
      case "in" : return 160;
      case "io" : return 161;
      case "ip" : return 162;
      case "iq" : return 163;
      case "ir" : return 164;
      case "is" : return 165;
      case "it" : return 166;
      case "iu" : return 167;
      case "iv" : return 168;
      case "iw" : return 169;
      case "ix" : return 170;
      case "jk" : return 171;
      case "jl" : return 172;
      case "jm" : return 173;
      case "jn" : return 174;
      case "jo" : return 175;
      case "jp" : return 176;
      case "jq" : return 177;
      case "jr" : return 178;
      case "js" : return 179;
      case "jt" : return 180;
      case "ju" : return 181;
      case "jv" : return 182;
      case "jw" : return 183;
      case "jx" : return 184;
      case "kl" : return 185;
      case "km" : return 186;
      case "kn" : return 187;
      case "ko" : return 188;
      case "kp" : return 189;
      case "kq" : return 190;
      case "kr" : return 191;
      case "ks" : return 192;
      case "kt" : return 193;
      case "ku" : return 194;
      case "kv" : return 195;
      case "kw" : return 196;
      case "kx" : return 197;
      case "lm" : return 198;
      case "ln" : return 199;
      case "lo" : return 200;
      case "lp" : return 201;
      case "lq" : return 202;
      case "lr" : return 203;
      case "ls" : return 204;
      case "lt" : return 205;
      case "lu" : return 206;
      case "lv" : return 207;
      case "lw" : return 208;
      case "lx" : return 209;
      case "mn" : return 210;
      case "mo" : return 211;
      case "mp" : return 212;
      case "mq" : return 213;
      case "mr" : return 214;
      case "ms" : return 215;
      case "mt" : return 216;
      case "mu" : return 217;
      case "mv" : return 218;
      case "mw" : return 219;
      case "mx" : return 220;
      case "no" : return 221;
      case "np" : return 222;
      case "nq" : return 223;
      case "nr" : return 224;
      case "ns" : return 225;
      case "nt" : return 226;
      case "nu" : return 227;
      case "nv" : return 228;
      case "nw" : return 229;
      case "nx" : return 230;
      case "op" : return 231;
      case "oq" : return 232;
      case "or" : return 233;
      case "os" : return 234;
      case "ot" : return 235;
      case "ou" : return 236;
      case "ov" : return 237;
      case "ow" : return 238;
      case "ox" : return 239;
      case "pq" : return 240;
      case "pr" : return 241;
      case "ps" : return 242;
      case "pt" : return 243;
      case "pu" : return 244;
      case "pv" : return 245;
      case "pw" : return 246;
      case "px" : return 247;
      case "qr" : return 248;
      case "qs" : return 249;
      case "qt" : return 250;
      case "qu" : return 251;
      case "qv" : return 252;
      case "qw" : return 253;
      case "qx" : return 254;
      case "rs" : return 255;
      default: return -1;
    }
  }
  */
}
