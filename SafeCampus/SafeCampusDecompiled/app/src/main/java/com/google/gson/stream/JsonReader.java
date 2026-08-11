package com.google.gson.stream;

import com.google.gson.Strictness;
import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.TroubleshootingGuide;
import com.google.gson.internal.bind.JsonTreeReader;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class JsonReader implements Closeable {
    static final int BUFFER_SIZE = 1024;
    private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
    private static final int NUMBER_CHAR_DECIMAL = 3;
    private static final int NUMBER_CHAR_DIGIT = 2;
    private static final int NUMBER_CHAR_EXP_DIGIT = 7;
    private static final int NUMBER_CHAR_EXP_E = 5;
    private static final int NUMBER_CHAR_EXP_SIGN = 6;
    private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
    private static final int NUMBER_CHAR_NONE = 0;
    private static final int NUMBER_CHAR_SIGN = 1;
    private static final int PEEKED_BEGIN_ARRAY = 3;
    private static final int PEEKED_BEGIN_OBJECT = 1;
    private static final int PEEKED_BUFFERED = 11;
    private static final int PEEKED_DOUBLE_QUOTED = 9;
    private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
    private static final int PEEKED_END_ARRAY = 4;
    private static final int PEEKED_END_OBJECT = 2;
    private static final int PEEKED_EOF = 17;
    private static final int PEEKED_FALSE = 6;
    private static final int PEEKED_LONG = 15;
    private static final int PEEKED_NONE = 0;
    private static final int PEEKED_NULL = 7;
    private static final int PEEKED_NUMBER = 16;
    private static final int PEEKED_SINGLE_QUOTED = 8;
    private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
    private static final int PEEKED_TRUE = 5;
    private static final int PEEKED_UNQUOTED = 10;
    private static final int PEEKED_UNQUOTED_NAME = 14;
    private final Reader in;
    private int[] pathIndices;
    private String[] pathNames;
    private long peekedLong;
    private int peekedNumberLength;
    private String peekedString;
    private int stackSize;
    private Strictness strictness = Strictness.LEGACY_STRICT;
    private final char[] buffer = new char[BUFFER_SIZE];
    private int pos = 0;
    private int limit = 0;
    private int lineNumber = 0;
    private int lineStart = 0;
    int peeked = 0;
    private int[] stack = new int[32];

    public JsonReader(Reader in) {
        this.stackSize = 0;
        int[] iArr = this.stack;
        int i = this.stackSize;
        this.stackSize = i + 1;
        iArr[i] = 6;
        this.pathNames = new String[32];
        this.pathIndices = new int[32];
        this.in = (Reader) Objects.requireNonNull(in, "in == null");
    }

    @Deprecated
    public final void setLenient(boolean lenient) {
        setStrictness(lenient ? Strictness.LENIENT : Strictness.LEGACY_STRICT);
    }

    public final boolean isLenient() {
        return this.strictness == Strictness.LENIENT;
    }

    public final void setStrictness(Strictness strictness) {
        Objects.requireNonNull(strictness);
        this.strictness = strictness;
    }

    public final Strictness getStrictness() {
        return this.strictness;
    }

    public void beginArray() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 3) {
            push(1);
            this.pathIndices[this.stackSize - 1] = 0;
            this.peeked = 0;
            return;
        }
        throw unexpectedTokenError("BEGIN_ARRAY");
    }

    public void endArray() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 4) {
            this.stackSize--;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            this.peeked = 0;
            return;
        }
        throw unexpectedTokenError("END_ARRAY");
    }

    public void beginObject() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 1) {
            push(3);
            this.peeked = 0;
            return;
        }
        throw unexpectedTokenError("BEGIN_OBJECT");
    }

    public void endObject() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 2) {
            this.stackSize--;
            this.pathNames[this.stackSize] = null;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            this.peeked = 0;
            return;
        }
        throw unexpectedTokenError("END_OBJECT");
    }

    public boolean hasNext() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        return (p == 2 || p == 4 || p == PEEKED_EOF) ? false : true;
    }

    public JsonToken peek() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        switch (p) {
            case 1:
                return JsonToken.BEGIN_OBJECT;
            case 2:
                return JsonToken.END_OBJECT;
            case 3:
                return JsonToken.BEGIN_ARRAY;
            case 4:
                return JsonToken.END_ARRAY;
            case 5:
            case 6:
                return JsonToken.BOOLEAN;
            case 7:
                return JsonToken.NULL;
            case PEEKED_SINGLE_QUOTED /* 8 */:
            case PEEKED_DOUBLE_QUOTED /* 9 */:
            case PEEKED_UNQUOTED /* 10 */:
            case PEEKED_BUFFERED /* 11 */:
                return JsonToken.STRING;
            case PEEKED_SINGLE_QUOTED_NAME /* 12 */:
            case PEEKED_DOUBLE_QUOTED_NAME /* 13 */:
            case PEEKED_UNQUOTED_NAME /* 14 */:
                return JsonToken.NAME;
            case PEEKED_LONG /* 15 */:
            case PEEKED_NUMBER /* 16 */:
                return JsonToken.NUMBER;
            case PEEKED_EOF /* 17 */:
                return JsonToken.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    int doPeek() throws IOException {
        int peekStack = this.stack[this.stackSize - 1];
        if (peekStack == 1) {
            this.stack[this.stackSize - 1] = 2;
        } else if (peekStack == 2) {
            switch (nextNonWhitespace(true)) {
                case 44:
                    break;
                case 59:
                    checkLenient();
                    break;
                case 93:
                    this.peeked = 4;
                    return 4;
                default:
                    throw syntaxError("Unterminated array");
            }
        } else {
            if (peekStack == 3 || peekStack == 5) {
                this.stack[this.stackSize - 1] = 4;
                if (peekStack == 5) {
                    switch (nextNonWhitespace(true)) {
                        case 44:
                            break;
                        case 59:
                            checkLenient();
                            break;
                        case 125:
                            this.peeked = 2;
                            return 2;
                        default:
                            throw syntaxError("Unterminated object");
                    }
                }
                int c = nextNonWhitespace(true);
                switch (c) {
                    case 34:
                        this.peeked = PEEKED_DOUBLE_QUOTED_NAME;
                        return PEEKED_DOUBLE_QUOTED_NAME;
                    case 39:
                        checkLenient();
                        this.peeked = PEEKED_SINGLE_QUOTED_NAME;
                        return PEEKED_SINGLE_QUOTED_NAME;
                    case 125:
                        if (peekStack != 5) {
                            this.peeked = 2;
                            return 2;
                        }
                        throw syntaxError("Expected name");
                    default:
                        checkLenient();
                        this.pos--;
                        if (isLiteral((char) c)) {
                            this.peeked = PEEKED_UNQUOTED_NAME;
                            return PEEKED_UNQUOTED_NAME;
                        }
                        throw syntaxError("Expected name");
                }
            }
            if (peekStack == 4) {
                this.stack[this.stackSize - 1] = 5;
                switch (nextNonWhitespace(true)) {
                    case 58:
                        break;
                    case 61:
                        checkLenient();
                        if ((this.pos < this.limit || fillBuffer(1)) && this.buffer[this.pos] == '>') {
                            this.pos++;
                        }
                        break;
                    default:
                        throw syntaxError("Expected ':'");
                }
            } else if (peekStack == 6) {
                if (this.strictness == Strictness.LENIENT) {
                    consumeNonExecutePrefix();
                }
                this.stack[this.stackSize - 1] = 7;
            } else if (peekStack == 7) {
                if (nextNonWhitespace(false) == -1) {
                    this.peeked = PEEKED_EOF;
                    return PEEKED_EOF;
                }
                checkLenient();
                this.pos--;
            } else if (peekStack == PEEKED_SINGLE_QUOTED) {
                throw new IllegalStateException("JsonReader is closed");
            }
        }
        switch (nextNonWhitespace(true)) {
            case 34:
                this.peeked = PEEKED_DOUBLE_QUOTED;
                return PEEKED_DOUBLE_QUOTED;
            case 39:
                checkLenient();
                this.peeked = PEEKED_SINGLE_QUOTED;
                return PEEKED_SINGLE_QUOTED;
            case 44:
            case 59:
                break;
            case 91:
                this.peeked = 3;
                return 3;
            case 93:
                if (peekStack == 1) {
                    this.peeked = 4;
                    return 4;
                }
                break;
            case 123:
                this.peeked = 1;
                return 1;
            default:
                this.pos--;
                int result = peekKeyword();
                if (result != 0) {
                    return result;
                }
                int result2 = peekNumber();
                if (result2 != 0) {
                    return result2;
                }
                if (!isLiteral(this.buffer[this.pos])) {
                    throw syntaxError("Expected value");
                }
                checkLenient();
                this.peeked = PEEKED_UNQUOTED;
                return PEEKED_UNQUOTED;
        }
        if (peekStack == 1 || peekStack == 2) {
            checkLenient();
            this.pos--;
            this.peeked = 7;
            return 7;
        }
        throw syntaxError("Unexpected value");
    }

    private int peekKeyword() throws IOException {
        String keyword;
        String keywordUpper;
        int peeking;
        char c = this.buffer[this.pos];
        if (c == 't' || c == 'T') {
            keyword = "true";
            keywordUpper = "TRUE";
            peeking = 5;
        } else if (c == 'f' || c == 'F') {
            keyword = "false";
            keywordUpper = "FALSE";
            peeking = 6;
        } else {
            if (c != 'n' && c != 'N') {
                return 0;
            }
            keyword = "null";
            keywordUpper = "NULL";
            peeking = 7;
        }
        boolean allowsUpperCased = this.strictness != Strictness.STRICT;
        int length = keyword.length();
        for (int i = 0; i < length; i++) {
            if (this.pos + i >= this.limit && !fillBuffer(i + 1)) {
                return 0;
            }
            char c2 = this.buffer[this.pos + i];
            boolean matched = c2 == keyword.charAt(i) || (allowsUpperCased && c2 == keywordUpper.charAt(i));
            if (!matched) {
                return 0;
            }
        }
        if ((this.pos + length < this.limit || fillBuffer(length + 1)) && isLiteral(this.buffer[this.pos + length])) {
            return 0;
        }
        this.pos += length;
        this.peeked = peeking;
        return peeking;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0033  */
    /* JADX WARN: Code duplicated, block: B:19:0x003d  */
    /* JADX WARN: Code duplicated, block: B:24:0x0045 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:25:0x0047  */
    /* JADX WARN: Code duplicated, block: B:27:0x004a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:28:0x004c  */
    /* JADX WARN: Code duplicated, block: B:29:0x004f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:30:0x0051  */
    /* JADX WARN: Code duplicated, block: B:32:0x0054 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:33:0x0056  */
    /* JADX WARN: Code duplicated, block: B:83:0x00d5 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:89:0x0049 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:90:0x0053 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:91:0x0058 A[SYNTHETIC] */
    private int peekNumber() throws IOException {
        char c;
        long j;
        char[] buffer = this.buffer;
        int p = this.pos;
        int l = this.limit;
        long value = 0;
        boolean negative = false;
        boolean fitsInLong = true;
        int last = 0;
        int i = 0;
        while (true) {
            boolean z = false;
            if (p + i == l) {
                if (i == buffer.length) {
                    return 0;
                }
                if (!fillBuffer(i + 1)) {
                    j = 0;
                } else {
                    p = this.pos;
                    l = this.limit;
                    c = buffer[p + i];
                    j = 0;
                    switch (c) {
                        case '+':
                            if (last == 5) {
                                return 0;
                            }
                            last = 6;
                            continue;
                            continue;
                            i++;
                            break;
                            break;
                        case '-':
                            if (last == 0) {
                                negative = true;
                                last = 1;
                                continue;
                                continue;
                            } else {
                                if (last == 5) {
                                    return 0;
                                }
                                last = 6;
                            }
                            i++;
                            break;
                        case '.':
                            if (last == 2) {
                                return 0;
                            }
                            last = 3;
                            continue;
                            continue;
                            i++;
                            break;
                            break;
                        case 'E':
                        case 'e':
                            if (last == 2) {
                            }
                            last = 5;
                            continue;
                            continue;
                            i++;
                            break;
                        default:
                            if (c >= '0') {
                            }
                            if (isLiteral(c)) {
                                return 0;
                            }
                            break;
                    }
                }
            } else {
                c = buffer[p + i];
                j = 0;
                switch (c) {
                    case '+':
                        if (last == 5) {
                            return 0;
                        }
                        last = 6;
                        continue;
                        continue;
                        i++;
                        break;
                        break;
                    case '-':
                        if (last == 0) {
                            negative = true;
                            last = 1;
                            continue;
                            continue;
                        } else {
                            if (last == 5) {
                                return 0;
                            }
                            last = 6;
                        }
                        i++;
                        break;
                    case '.':
                        if (last == 2) {
                            return 0;
                        }
                        last = 3;
                        continue;
                        continue;
                        i++;
                        break;
                        break;
                    case 'E':
                    case 'e':
                        if (last == 2 && last != 4) {
                            return 0;
                        }
                        last = 5;
                        continue;
                        continue;
                        i++;
                        break;
                        break;
                    default:
                        if (c >= '0' || c > '9') {
                            if (isLiteral(c)) {
                                return 0;
                            }
                        } else {
                            if (last == 1 || last == 0) {
                                value = -(c - '0');
                                last = 2;
                            } else if (last == 2) {
                                if (value == 0) {
                                    return 0;
                                }
                                long value2 = (10 * value) - ((long) (c - '0'));
                                if (value > MIN_INCOMPLETE_INTEGER || (value == MIN_INCOMPLETE_INTEGER && value2 < value)) {
                                    z = true;
                                }
                                fitsInLong &= z;
                                value = value2;
                            } else if (last == 3) {
                                last = 4;
                            } else if (last == 5 || last == 6) {
                                last = 7;
                            }
                            i++;
                        }
                        break;
                }
            }
        }
        if (last == 2 && fitsInLong && ((value != Long.MIN_VALUE || negative) && (value != j || !negative))) {
            this.peekedLong = negative ? value : -value;
            this.pos += i;
            this.peeked = PEEKED_LONG;
            return PEEKED_LONG;
        }
        if (last != 2 && last != 4 && last != 7) {
            return 0;
        }
        this.peekedNumberLength = i;
        this.peeked = PEEKED_NUMBER;
        return PEEKED_NUMBER;
    }

    private boolean isLiteral(char c) throws IOException {
        switch (c) {
            case PEEKED_DOUBLE_QUOTED /* 9 */:
            case PEEKED_UNQUOTED /* 10 */:
            case PEEKED_SINGLE_QUOTED_NAME /* 12 */:
            case PEEKED_DOUBLE_QUOTED_NAME /* 13 */:
            case ' ':
            case ',':
            case ':':
            case '[':
            case ']':
            case '{':
            case '}':
                return false;
            case '#':
            case '/':
            case ';':
            case '=':
            case '\\':
                checkLenient();
                return false;
            default:
                return true;
        }
    }

    public String nextName() throws IOException {
        String result;
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == PEEKED_UNQUOTED_NAME) {
            result = nextUnquotedValue();
        } else if (p == PEEKED_SINGLE_QUOTED_NAME) {
            result = nextQuotedValue('\'');
        } else if (p == PEEKED_DOUBLE_QUOTED_NAME) {
            result = nextQuotedValue('\"');
        } else {
            throw unexpectedTokenError("a name");
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = result;
        return result;
    }

    public String nextString() throws IOException {
        String result;
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == PEEKED_UNQUOTED) {
            result = nextUnquotedValue();
        } else if (p == PEEKED_SINGLE_QUOTED) {
            result = nextQuotedValue('\'');
        } else if (p == PEEKED_DOUBLE_QUOTED) {
            result = nextQuotedValue('\"');
        } else if (p == PEEKED_BUFFERED) {
            result = this.peekedString;
            this.peekedString = null;
        } else if (p == PEEKED_LONG) {
            result = Long.toString(this.peekedLong);
        } else if (p == PEEKED_NUMBER) {
            result = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            throw unexpectedTokenError("a string");
        }
        this.peeked = 0;
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return result;
    }

    public boolean nextBoolean() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 5) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return true;
        }
        if (p == 6) {
            this.peeked = 0;
            int[] iArr2 = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr2[i2] = iArr2[i2] + 1;
            return false;
        }
        throw unexpectedTokenError("a boolean");
    }

    public void nextNull() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == 7) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return;
        }
        throw unexpectedTokenError("null");
    }

    public double nextDouble() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == PEEKED_LONG) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return this.peekedLong;
        }
        if (p == PEEKED_NUMBER) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else if (p == PEEKED_SINGLE_QUOTED || p == PEEKED_DOUBLE_QUOTED) {
            this.peekedString = nextQuotedValue(p == PEEKED_SINGLE_QUOTED ? '\'' : '\"');
        } else if (p == PEEKED_UNQUOTED) {
            this.peekedString = nextUnquotedValue();
        } else if (p != PEEKED_BUFFERED) {
            throw unexpectedTokenError("a double");
        }
        this.peeked = PEEKED_BUFFERED;
        double result = Double.parseDouble(this.peekedString);
        if (this.strictness != Strictness.LENIENT && (Double.isNaN(result) || Double.isInfinite(result))) {
            throw syntaxError("JSON forbids NaN and infinities: " + result);
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] iArr2 = this.pathIndices;
        int i2 = this.stackSize - 1;
        iArr2[i2] = iArr2[i2] + 1;
        return result;
    }

    public long nextLong() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == PEEKED_LONG) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return this.peekedLong;
        }
        if (p == PEEKED_NUMBER) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else if (p == PEEKED_SINGLE_QUOTED || p == PEEKED_DOUBLE_QUOTED || p == PEEKED_UNQUOTED) {
            if (p == PEEKED_UNQUOTED) {
                this.peekedString = nextUnquotedValue();
            } else {
                this.peekedString = nextQuotedValue(p == PEEKED_SINGLE_QUOTED ? '\'' : '\"');
            }
            try {
                long result = Long.parseLong(this.peekedString);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i2 = this.stackSize - 1;
                iArr2[i2] = iArr2[i2] + 1;
                return result;
            } catch (NumberFormatException e) {
            }
        } else {
            throw unexpectedTokenError("a long");
        }
        this.peeked = PEEKED_BUFFERED;
        double asDouble = Double.parseDouble(this.peekedString);
        long result2 = (long) asDouble;
        if (result2 != asDouble) {
            throw new NumberFormatException("Expected a long but was " + this.peekedString + locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] iArr3 = this.pathIndices;
        int i3 = this.stackSize - 1;
        iArr3[i3] = iArr3[i3] + 1;
        return result2;
    }

    private String nextQuotedValue(char quote) throws IOException {
        char[] buffer = this.buffer;
        StringBuilder builder = null;
        do {
            int c = this.pos;
            int l = this.limit;
            int start = c;
            while (c < l) {
                int p = c + 1;
                char c2 = buffer[c];
                if (this.strictness == Strictness.STRICT && c2 < ' ') {
                    throw syntaxError("Unescaped control characters (\\u0000-\\u001F) are not allowed in strict mode");
                }
                if (c2 == quote) {
                    this.pos = p;
                    int len = (p - start) - 1;
                    if (builder == null) {
                        return new String(buffer, start, len);
                    }
                    builder.append(buffer, start, len);
                    return builder.toString();
                }
                if (c2 == '\\') {
                    this.pos = p;
                    int len2 = (p - start) - 1;
                    if (builder == null) {
                        int estimatedLength = (len2 + 1) * 2;
                        builder = new StringBuilder(Math.max(estimatedLength, PEEKED_NUMBER));
                    }
                    builder.append(buffer, start, len2);
                    builder.append(readEscapeCharacter());
                    int p2 = this.pos;
                    l = this.limit;
                    start = p2;
                    c = p2;
                } else {
                    if (c2 == PEEKED_UNQUOTED) {
                        this.lineNumber++;
                        this.lineStart = p;
                    }
                    c = p;
                }
            }
            if (builder == null) {
                int estimatedLength2 = (c - start) * 2;
                builder = new StringBuilder(Math.max(estimatedLength2, PEEKED_NUMBER));
            }
            builder.append(buffer, start, c - start);
            this.pos = c;
        } while (fillBuffer(1));
        throw syntaxError("Unterminated string");
    }

    private String nextUnquotedValue() throws IOException {
        StringBuilder builder = null;
        int i = 0;
        while (true) {
            if (this.pos + i < this.limit) {
                switch (this.buffer[this.pos + i]) {
                    case PEEKED_DOUBLE_QUOTED /* 9 */:
                    case PEEKED_UNQUOTED /* 10 */:
                    case PEEKED_SINGLE_QUOTED_NAME /* 12 */:
                    case PEEKED_DOUBLE_QUOTED_NAME /* 13 */:
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                        break;
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\':
                        checkLenient();
                        break;
                    default:
                        i++;
                        continue;
                }
            } else if (i < this.buffer.length) {
                if (fillBuffer(i + 1)) {
                }
            } else {
                if (builder == null) {
                    builder = new StringBuilder(Math.max(i, PEEKED_NUMBER));
                }
                builder.append(this.buffer, this.pos, i);
                this.pos += i;
                i = 0;
                if (!fillBuffer(1)) {
                }
            }
        }
        String result = builder == null ? new String(this.buffer, this.pos, i) : builder.append(this.buffer, this.pos, i).toString();
        this.pos += i;
        return result;
    }

    private void skipQuotedValue(char quote) throws IOException {
        char[] buffer = this.buffer;
        do {
            int c = this.pos;
            int l = this.limit;
            while (c < l) {
                int p = c + 1;
                char c2 = buffer[c];
                if (c2 == quote) {
                    this.pos = p;
                    return;
                }
                if (c2 == '\\') {
                    this.pos = p;
                    readEscapeCharacter();
                    int p2 = this.pos;
                    l = this.limit;
                    c = p2;
                } else {
                    if (c2 == PEEKED_UNQUOTED) {
                        this.lineNumber++;
                        this.lineStart = p;
                    }
                    c = p;
                }
            }
            this.pos = c;
        } while (fillBuffer(1));
        throw syntaxError("Unterminated string");
    }

    private void skipUnquotedValue() throws IOException {
        do {
            int i = 0;
            while (this.pos + i < this.limit) {
                switch (this.buffer[this.pos + i]) {
                    case PEEKED_DOUBLE_QUOTED /* 9 */:
                    case PEEKED_UNQUOTED /* 10 */:
                    case PEEKED_SINGLE_QUOTED_NAME /* 12 */:
                    case PEEKED_DOUBLE_QUOTED_NAME /* 13 */:
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                        break;
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\':
                        checkLenient();
                        break;
                    default:
                        i++;
                        break;
                }
                this.pos += i;
                return;
            }
            this.pos += i;
        } while (fillBuffer(1));
    }

    public int nextInt() throws IOException {
        int p = this.peeked;
        if (p == 0) {
            p = doPeek();
        }
        if (p == PEEKED_LONG) {
            int result = (int) this.peekedLong;
            if (this.peekedLong != result) {
                throw new NumberFormatException("Expected an int but was " + this.peekedLong + locationString());
            }
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return result;
        }
        if (p == PEEKED_NUMBER) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else if (p == PEEKED_SINGLE_QUOTED || p == PEEKED_DOUBLE_QUOTED || p == PEEKED_UNQUOTED) {
            if (p == PEEKED_UNQUOTED) {
                this.peekedString = nextUnquotedValue();
            } else {
                this.peekedString = nextQuotedValue(p == PEEKED_SINGLE_QUOTED ? '\'' : '\"');
            }
            try {
                int result2 = Integer.parseInt(this.peekedString);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i2 = this.stackSize - 1;
                iArr2[i2] = iArr2[i2] + 1;
                return result2;
            } catch (NumberFormatException e) {
            }
        } else {
            throw unexpectedTokenError("an int");
        }
        this.peeked = PEEKED_BUFFERED;
        double asDouble = Double.parseDouble(this.peekedString);
        int result3 = (int) asDouble;
        if (result3 != asDouble) {
            throw new NumberFormatException("Expected an int but was " + this.peekedString + locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] iArr3 = this.pathIndices;
        int i3 = this.stackSize - 1;
        iArr3[i3] = iArr3[i3] + 1;
        return result3;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.peeked = 0;
        this.stack[0] = PEEKED_SINGLE_QUOTED;
        this.stackSize = 1;
        this.in.close();
    }

    public void skipValue() throws IOException {
        int count = 0;
        do {
            int p = this.peeked;
            if (p == 0) {
                p = doPeek();
            }
            switch (p) {
                case 1:
                    push(3);
                    count++;
                    break;
                case 2:
                    if (count == 0) {
                        this.pathNames[this.stackSize - 1] = null;
                    }
                    this.stackSize--;
                    count--;
                    break;
                case 3:
                    push(1);
                    count++;
                    break;
                case 4:
                    this.stackSize--;
                    count--;
                    break;
                case PEEKED_SINGLE_QUOTED /* 8 */:
                    skipQuotedValue('\'');
                    break;
                case PEEKED_DOUBLE_QUOTED /* 9 */:
                    skipQuotedValue('\"');
                    break;
                case PEEKED_UNQUOTED /* 10 */:
                    skipUnquotedValue();
                    break;
                case PEEKED_SINGLE_QUOTED_NAME /* 12 */:
                    skipQuotedValue('\'');
                    if (count == 0) {
                        this.pathNames[this.stackSize - 1] = "<skipped>";
                    }
                    break;
                case PEEKED_DOUBLE_QUOTED_NAME /* 13 */:
                    skipQuotedValue('\"');
                    if (count == 0) {
                        this.pathNames[this.stackSize - 1] = "<skipped>";
                    }
                    break;
                case PEEKED_UNQUOTED_NAME /* 14 */:
                    skipUnquotedValue();
                    if (count == 0) {
                        this.pathNames[this.stackSize - 1] = "<skipped>";
                    }
                    break;
                case PEEKED_NUMBER /* 16 */:
                    this.pos += this.peekedNumberLength;
                    break;
                case PEEKED_EOF /* 17 */:
                    return;
            }
            this.peeked = 0;
        } while (count > 0);
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
    }

    private void push(int newTop) {
        if (this.stackSize == this.stack.length) {
            int newLength = this.stackSize * 2;
            this.stack = Arrays.copyOf(this.stack, newLength);
            this.pathIndices = Arrays.copyOf(this.pathIndices, newLength);
            this.pathNames = (String[]) Arrays.copyOf(this.pathNames, newLength);
        }
        int[] iArr = this.stack;
        int i = this.stackSize;
        this.stackSize = i + 1;
        iArr[i] = newTop;
    }

    private boolean fillBuffer(int minimum) throws IOException {
        char[] buffer = this.buffer;
        this.lineStart -= this.pos;
        if (this.limit != this.pos) {
            this.limit -= this.pos;
            System.arraycopy(buffer, this.pos, buffer, 0, this.limit);
        } else {
            this.limit = 0;
        }
        this.pos = 0;
        do {
            int total = this.in.read(buffer, this.limit, buffer.length - this.limit);
            if (total == -1) {
                return false;
            }
            this.limit += total;
            if (this.lineNumber == 0 && this.lineStart == 0 && this.limit > 0 && buffer[0] == 65279) {
                this.pos++;
                this.lineStart++;
                minimum++;
            }
        } while (this.limit < minimum);
        return true;
    }

    private int nextNonWhitespace(boolean throwOnEof) throws IOException {
        char[] buffer = this.buffer;
        int p = this.pos;
        int l = this.limit;
        while (true) {
            if (p == l) {
                this.pos = p;
                if (fillBuffer(1)) {
                    p = this.pos;
                    l = this.limit;
                } else {
                    if (throwOnEof) {
                        throw new EOFException("End of input" + locationString());
                    }
                    return -1;
                }
            }
            int p2 = p + 1;
            char c = buffer[p];
            if (c == PEEKED_UNQUOTED) {
                this.lineNumber++;
                this.lineStart = p2;
            } else if (c != ' ' && c != PEEKED_DOUBLE_QUOTED_NAME && c != PEEKED_DOUBLE_QUOTED) {
                if (c == '/') {
                    this.pos = p2;
                    if (p2 == l) {
                        this.pos--;
                        boolean charsLoaded = fillBuffer(2);
                        this.pos++;
                        if (!charsLoaded) {
                            return c;
                        }
                    }
                    checkLenient();
                    char peek = buffer[this.pos];
                    switch (peek) {
                        case '*':
                            this.pos++;
                            if (skipTo("*/")) {
                                int p3 = this.pos + 2;
                                l = this.limit;
                                p = p3;
                            } else {
                                throw syntaxError("Unterminated comment");
                            }
                            break;
                        case '/':
                            this.pos++;
                            skipToEndOfLine();
                            int p4 = this.pos;
                            l = this.limit;
                            p = p4;
                            break;
                        default:
                            return c;
                    }
                } else if (c == '#') {
                    this.pos = p2;
                    checkLenient();
                    skipToEndOfLine();
                    int p5 = this.pos;
                    l = this.limit;
                    p = p5;
                } else {
                    this.pos = p2;
                    return c;
                }
            }
            p = p2;
        }
    }

    private void checkLenient() throws MalformedJsonException {
        if (this.strictness != Strictness.LENIENT) {
            throw syntaxError("Use JsonReader.setStrictness(Strictness.LENIENT) to accept malformed JSON");
        }
    }

    private void skipToEndOfLine() throws IOException {
        char c;
        do {
            if (this.pos < this.limit || fillBuffer(1)) {
                char[] cArr = this.buffer;
                int i = this.pos;
                this.pos = i + 1;
                c = cArr[i];
                if (c == PEEKED_UNQUOTED) {
                    this.lineNumber++;
                    this.lineStart = this.pos;
                    return;
                }
            } else {
                return;
            }
        } while (c != PEEKED_DOUBLE_QUOTED_NAME);
    }

    private boolean skipTo(String toFind) throws IOException {
        int length = toFind.length();
        while (true) {
            if (this.pos + length <= this.limit || fillBuffer(length)) {
                if (this.buffer[this.pos] == PEEKED_UNQUOTED) {
                    this.lineNumber++;
                    this.lineStart = this.pos + 1;
                } else {
                    for (int c = 0; c < length; c++) {
                        if (this.buffer[this.pos + c] == toFind.charAt(c)) {
                        }
                    }
                    return true;
                }
                int c2 = this.pos;
                this.pos = c2 + 1;
            } else {
                return false;
            }
        }
    }

    public String toString() {
        return getClass().getSimpleName() + locationString();
    }

    String locationString() {
        int line = this.lineNumber + 1;
        int column = (this.pos - this.lineStart) + 1;
        return " at line " + line + " column " + column + " path " + getPath();
    }

    private String getPath(boolean usePreviousPath) {
        StringBuilder result = new StringBuilder().append('$');
        for (int i = 0; i < this.stackSize; i++) {
            int scope = this.stack[i];
            switch (scope) {
                case 1:
                case 2:
                    int pathIndex = this.pathIndices[i];
                    if (usePreviousPath && pathIndex > 0 && i == this.stackSize - 1) {
                        pathIndex--;
                    }
                    result.append('[').append(pathIndex).append(']');
                    break;
                case 3:
                case 4:
                case 5:
                    result.append('.');
                    if (this.pathNames[i] != null) {
                        result.append(this.pathNames[i]);
                    }
                    break;
                case 6:
                case 7:
                case PEEKED_SINGLE_QUOTED /* 8 */:
                    break;
                default:
                    throw new AssertionError("Unknown scope value: " + scope);
            }
        }
        return result.toString();
    }

    public String getPath() {
        return getPath(false);
    }

    public String getPreviousPath() {
        return getPath(true);
    }

    private char readEscapeCharacter() throws IOException {
        int i;
        if (this.pos == this.limit && !fillBuffer(1)) {
            throw syntaxError("Unterminated escape sequence");
        }
        char[] cArr = this.buffer;
        int i2 = this.pos;
        this.pos = i2 + 1;
        char escaped = cArr[i2];
        switch (escaped) {
            case PEEKED_UNQUOTED /* 10 */:
                if (this.strictness == Strictness.STRICT) {
                    throw syntaxError("Cannot escape a newline character in strict mode");
                }
                this.lineNumber++;
                this.lineStart = this.pos;
                break;
            case '\'':
                if (this.strictness == Strictness.STRICT) {
                    throw syntaxError("Invalid escaped character \"'\" in strict mode");
                }
            case '\"':
            case '/':
            case '\\':
                return escaped;
            case 'b':
                return '\b';
            case 'f':
                return '\f';
            case 'n':
                return '\n';
            case 'r':
                return '\r';
            case 't':
                return '\t';
            case 'u':
                if (this.pos + 4 > this.limit && !fillBuffer(4)) {
                    throw syntaxError("Unterminated escape sequence");
                }
                int result = 0;
                int i3 = this.pos;
                int end = i3 + 4;
                while (i3 < end) {
                    char c = this.buffer[i3];
                    int result2 = result << 4;
                    if (c >= '0' && c <= '9') {
                        i = c - '0';
                    } else if (c >= 'a' && c <= 'f') {
                        i = (c - 'a') + PEEKED_UNQUOTED;
                    } else if (c >= 'A' && c <= 'F') {
                        i = (c - 'A') + PEEKED_UNQUOTED;
                    } else {
                        throw syntaxError("Malformed Unicode escape \\u" + new String(this.buffer, this.pos, 4));
                    }
                    result = result2 + i;
                    i3++;
                }
                this.pos += 4;
                return (char) result;
            default:
                throw syntaxError("Invalid escape sequence");
        }
    }

    private MalformedJsonException syntaxError(String message) throws MalformedJsonException {
        throw new MalformedJsonException(message + locationString() + "\nSee " + TroubleshootingGuide.createUrl("malformed-json"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IllegalStateException unexpectedTokenError(String expected) throws IOException {
        JsonToken peeked = peek();
        String troubleshootingId = peeked == JsonToken.NULL ? "adapter-not-null-safe" : "unexpected-json-structure";
        return new IllegalStateException("Expected " + expected + " but was " + peek() + locationString() + "\nSee " + TroubleshootingGuide.createUrl(troubleshootingId));
    }

    private void consumeNonExecutePrefix() throws IOException {
        nextNonWhitespace(true);
        this.pos--;
        if (this.pos + 5 > this.limit && !fillBuffer(5)) {
            return;
        }
        int p = this.pos;
        char[] buf = this.buffer;
        if (buf[p] != ')' || buf[p + 1] != ']' || buf[p + 2] != '}' || buf[p + 3] != '\'' || buf[p + 4] != PEEKED_UNQUOTED) {
            return;
        }
        this.pos += 5;
    }

    static {
        JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess() { // from class: com.google.gson.stream.JsonReader.1
            @Override // com.google.gson.internal.JsonReaderInternalAccess
            public void promoteNameToValue(JsonReader reader) throws IOException {
                if (reader instanceof JsonTreeReader) {
                    ((JsonTreeReader) reader).promoteNameToValue();
                    return;
                }
                int p = reader.peeked;
                if (p == 0) {
                    p = reader.doPeek();
                }
                if (p == JsonReader.PEEKED_DOUBLE_QUOTED_NAME) {
                    reader.peeked = JsonReader.PEEKED_DOUBLE_QUOTED;
                } else if (p == JsonReader.PEEKED_SINGLE_QUOTED_NAME) {
                    reader.peeked = JsonReader.PEEKED_SINGLE_QUOTED;
                } else {
                    if (p != JsonReader.PEEKED_UNQUOTED_NAME) {
                        throw reader.unexpectedTokenError("a name");
                    }
                    reader.peeked = JsonReader.PEEKED_UNQUOTED;
                }
            }
        };
    }
}
