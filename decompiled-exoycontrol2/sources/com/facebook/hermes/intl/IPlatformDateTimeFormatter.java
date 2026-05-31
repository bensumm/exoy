package com.facebook.hermes.intl;

import java.text.AttributedCharacterIterator;

/* JADX INFO: loaded from: classes.dex */
public interface IPlatformDateTimeFormatter {
    void configure(ILocaleObject<?> iLocaleObject, String str, String str2, FormatMatcher formatMatcher, WeekDay weekDay, Era era, Year year, Month month, Day day, Hour hour, Minute minute, Second second, TimeZoneName timeZoneName, HourCycle hourCycle, Object obj) throws JSRangeErrorException;

    String fieldToString(AttributedCharacterIterator.Attribute attribute, String str);

    String format(double d) throws JSRangeErrorException;

    AttributedCharacterIterator formatToParts(double d) throws JSRangeErrorException;

    String[] getAvailableLocales();

    String getDefaultCalendarName(ILocaleObject<?> iLocaleObject) throws JSRangeErrorException;

    HourCycle getDefaultHourCycle(ILocaleObject<?> iLocaleObject) throws JSRangeErrorException;

    String getDefaultNumberingSystem(ILocaleObject<?> iLocaleObject) throws JSRangeErrorException;

    String getDefaultTimeZone(ILocaleObject<?> iLocaleObject) throws JSRangeErrorException;

    public enum FormatMatcher {
        BESTFIT,
        BASIC;

        @Override // java.lang.Enum
        public String toString() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$FormatMatcher[ordinal()];
            if (i == 1) {
                return Constants.LOCALEMATCHER_BESTFIT;
            }
            if (i == 2) {
                return "basic";
            }
            throw new IllegalArgumentException();
        }
    }

    public enum HourCycle {
        H11,
        H12,
        H23,
        H24,
        UNDEFINED;

        @Override // java.lang.Enum
        public String toString() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$HourCycle[ordinal()];
            if (i == 1) {
                return "h11";
            }
            if (i == 2) {
                return "h12";
            }
            if (i == 3) {
                return "h23";
            }
            if (i == 4) {
                return "h24";
            }
            if (i == 5) {
                return "";
            }
            throw new IllegalArgumentException();
        }
    }

    public enum WeekDay {
        LONG,
        SHORT,
        NARROW,
        UNDEFINED;

        @Override // java.lang.Enum
        public String toString() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$WeekDay[ordinal()];
            if (i == 1) {
                return "long";
            }
            if (i == 2) {
                return "short";
            }
            if (i == 3) {
                return "narrow";
            }
            if (i == 4) {
                return "";
            }
            throw new IllegalArgumentException();
        }

        public String getSkeleonSymbol() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$WeekDay[ordinal()];
            if (i == 1) {
                return "EEEE";
            }
            if (i == 2) {
                return "EEE";
            }
            if (i == 3) {
                return "EEEEE";
            }
            if (i == 4) {
                return "";
            }
            throw new IllegalArgumentException();
        }
    }

    public enum Era {
        LONG,
        SHORT,
        NARROW,
        UNDEFINED;

        @Override // java.lang.Enum
        public String toString() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Era[ordinal()];
            if (i == 1) {
                return "long";
            }
            if (i == 2) {
                return "short";
            }
            if (i == 3) {
                return "narrow";
            }
            if (i == 4) {
                return "";
            }
            throw new IllegalArgumentException();
        }

        public String getSkeleonSymbol() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Era[ordinal()];
            if (i == 1) {
                return "GGGG";
            }
            if (i == 2) {
                return "GGG";
            }
            if (i == 3) {
                return "G5";
            }
            if (i == 4) {
                return "";
            }
            throw new IllegalArgumentException();
        }
    }

    public enum Year {
        NUMERIC,
        DIGIT2,
        UNDEFINED;

        @Override // java.lang.Enum
        public String toString() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Year[ordinal()];
            if (i == 1) {
                return Constants.COLLATION_OPTION_NUMERIC;
            }
            if (i == 2) {
                return "2-digit";
            }
            if (i == 3) {
                return "";
            }
            throw new IllegalArgumentException();
        }

        public String getSkeleonSymbol() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Year[ordinal()];
            if (i == 1) {
                return "yyyy";
            }
            if (i == 2) {
                return "yy";
            }
            if (i == 3) {
                return "";
            }
            throw new IllegalArgumentException();
        }
    }

    public enum Month {
        NUMERIC,
        DIGIT2,
        LONG,
        SHORT,
        NARROW,
        UNDEFINED;

        @Override // java.lang.Enum
        public String toString() {
            switch (AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Month[ordinal()]) {
                case 1:
                    return Constants.COLLATION_OPTION_NUMERIC;
                case 2:
                    return "2-digit";
                case 3:
                    return "long";
                case 4:
                    return "short";
                case 5:
                    return "narrow";
                case 6:
                    return "";
                default:
                    throw new IllegalArgumentException();
            }
        }

        public String getSkeleonSymbol() {
            switch (AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Month[ordinal()]) {
                case 1:
                    return "M";
                case 2:
                    return "MM";
                case 3:
                    return "MMMM";
                case 4:
                    return "MMM";
                case 5:
                    return "MMMMM";
                case 6:
                    return "";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public enum Day {
        NUMERIC,
        DIGIT2,
        UNDEFINED;

        @Override // java.lang.Enum
        public String toString() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Day[ordinal()];
            if (i == 1) {
                return Constants.COLLATION_OPTION_NUMERIC;
            }
            if (i == 2) {
                return "2-digit";
            }
            if (i == 3) {
                return "";
            }
            throw new IllegalArgumentException();
        }

        public String getSkeleonSymbol() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Day[ordinal()];
            if (i == 1) {
                return "d";
            }
            if (i == 2) {
                return "dd";
            }
            if (i == 3) {
                return "";
            }
            throw new IllegalArgumentException();
        }
    }

    public enum Hour {
        NUMERIC,
        DIGIT2,
        UNDEFINED;

        @Override // java.lang.Enum
        public String toString() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Hour[ordinal()];
            if (i == 1) {
                return Constants.COLLATION_OPTION_NUMERIC;
            }
            if (i == 2) {
                return "2-digit";
            }
            if (i == 3) {
                return "";
            }
            throw new IllegalArgumentException();
        }

        public String getSkeleonSymbol12() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Hour[ordinal()];
            if (i == 1) {
                return "h";
            }
            if (i == 2) {
                return "hh";
            }
            if (i == 3) {
                return "";
            }
            throw new IllegalArgumentException();
        }

        public String getSkeleonSymbol24() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Hour[ordinal()];
            if (i == 1) {
                return "k";
            }
            if (i == 2) {
                return "kk";
            }
            if (i == 3) {
                return "";
            }
            throw new IllegalArgumentException();
        }
    }

    public enum Minute {
        NUMERIC,
        DIGIT2,
        UNDEFINED;

        @Override // java.lang.Enum
        public String toString() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Minute[ordinal()];
            if (i == 1) {
                return Constants.COLLATION_OPTION_NUMERIC;
            }
            if (i == 2) {
                return "2-digit";
            }
            if (i == 3) {
                return "";
            }
            throw new IllegalArgumentException();
        }

        public String getSkeleonSymbol() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Minute[ordinal()];
            if (i == 1) {
                return "m";
            }
            if (i == 2) {
                return "mm";
            }
            if (i == 3) {
                return "";
            }
            throw new IllegalArgumentException();
        }
    }

    public enum Second {
        NUMERIC,
        DIGIT2,
        UNDEFINED;

        @Override // java.lang.Enum
        public String toString() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Second[ordinal()];
            if (i == 1) {
                return Constants.COLLATION_OPTION_NUMERIC;
            }
            if (i == 2) {
                return "2-digit";
            }
            if (i == 3) {
                return "";
            }
            throw new IllegalArgumentException();
        }

        public String getSkeleonSymbol() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Second[ordinal()];
            if (i == 1) {
                return "s";
            }
            if (i == 2) {
                return "ss";
            }
            if (i == 3) {
                return "";
            }
            throw new IllegalArgumentException();
        }
    }

    /* JADX INFO: renamed from: com.facebook.hermes.intl.IPlatformDateTimeFormatter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Day;
        static final /* synthetic */ int[] $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Era;
        static final /* synthetic */ int[] $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$FormatMatcher;
        static final /* synthetic */ int[] $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Hour;
        static final /* synthetic */ int[] $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$HourCycle;
        static final /* synthetic */ int[] $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Minute;
        static final /* synthetic */ int[] $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Month;
        static final /* synthetic */ int[] $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Second;
        static final /* synthetic */ int[] $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$TimeZoneName;
        static final /* synthetic */ int[] $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$WeekDay;
        static final /* synthetic */ int[] $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Year;

        static {
            int[] iArr = new int[TimeZoneName.values().length];
            $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$TimeZoneName = iArr;
            try {
                iArr[TimeZoneName.LONG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$TimeZoneName[TimeZoneName.SHORT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$TimeZoneName[TimeZoneName.UNDEFINED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[Second.values().length];
            $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Second = iArr2;
            try {
                iArr2[Second.NUMERIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Second[Second.DIGIT2.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Second[Second.UNDEFINED.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr3 = new int[Minute.values().length];
            $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Minute = iArr3;
            try {
                iArr3[Minute.NUMERIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Minute[Minute.DIGIT2.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Minute[Minute.UNDEFINED.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            int[] iArr4 = new int[Hour.values().length];
            $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Hour = iArr4;
            try {
                iArr4[Hour.NUMERIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Hour[Hour.DIGIT2.ordinal()] = 2;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Hour[Hour.UNDEFINED.ordinal()] = 3;
            } catch (NoSuchFieldError unused12) {
            }
            int[] iArr5 = new int[Day.values().length];
            $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Day = iArr5;
            try {
                iArr5[Day.NUMERIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Day[Day.DIGIT2.ordinal()] = 2;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Day[Day.UNDEFINED.ordinal()] = 3;
            } catch (NoSuchFieldError unused15) {
            }
            int[] iArr6 = new int[Month.values().length];
            $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Month = iArr6;
            try {
                iArr6[Month.NUMERIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Month[Month.DIGIT2.ordinal()] = 2;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Month[Month.LONG.ordinal()] = 3;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Month[Month.SHORT.ordinal()] = 4;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Month[Month.NARROW.ordinal()] = 5;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Month[Month.UNDEFINED.ordinal()] = 6;
            } catch (NoSuchFieldError unused21) {
            }
            int[] iArr7 = new int[Year.values().length];
            $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Year = iArr7;
            try {
                iArr7[Year.NUMERIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Year[Year.DIGIT2.ordinal()] = 2;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Year[Year.UNDEFINED.ordinal()] = 3;
            } catch (NoSuchFieldError unused24) {
            }
            int[] iArr8 = new int[Era.values().length];
            $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Era = iArr8;
            try {
                iArr8[Era.LONG.ordinal()] = 1;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Era[Era.SHORT.ordinal()] = 2;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Era[Era.NARROW.ordinal()] = 3;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$Era[Era.UNDEFINED.ordinal()] = 4;
            } catch (NoSuchFieldError unused28) {
            }
            int[] iArr9 = new int[WeekDay.values().length];
            $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$WeekDay = iArr9;
            try {
                iArr9[WeekDay.LONG.ordinal()] = 1;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$WeekDay[WeekDay.SHORT.ordinal()] = 2;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$WeekDay[WeekDay.NARROW.ordinal()] = 3;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$WeekDay[WeekDay.UNDEFINED.ordinal()] = 4;
            } catch (NoSuchFieldError unused32) {
            }
            int[] iArr10 = new int[HourCycle.values().length];
            $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$HourCycle = iArr10;
            try {
                iArr10[HourCycle.H11.ordinal()] = 1;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$HourCycle[HourCycle.H12.ordinal()] = 2;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$HourCycle[HourCycle.H23.ordinal()] = 3;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$HourCycle[HourCycle.H24.ordinal()] = 4;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$HourCycle[HourCycle.UNDEFINED.ordinal()] = 5;
            } catch (NoSuchFieldError unused37) {
            }
            int[] iArr11 = new int[FormatMatcher.values().length];
            $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$FormatMatcher = iArr11;
            try {
                iArr11[FormatMatcher.BESTFIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$FormatMatcher[FormatMatcher.BASIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused39) {
            }
        }
    }

    public enum TimeZoneName {
        LONG,
        SHORT,
        UNDEFINED;

        @Override // java.lang.Enum
        public String toString() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$TimeZoneName[ordinal()];
            if (i == 1) {
                return "long";
            }
            if (i == 2) {
                return "short";
            }
            if (i == 3) {
                return "";
            }
            throw new IllegalArgumentException();
        }

        public String getSkeleonSymbol() {
            int i = AnonymousClass1.$SwitchMap$com$facebook$hermes$intl$IPlatformDateTimeFormatter$TimeZoneName[ordinal()];
            if (i == 1) {
                return "VV";
            }
            if (i == 2) {
                return "O";
            }
            if (i == 3) {
                return "";
            }
            throw new IllegalArgumentException();
        }
    }
}
