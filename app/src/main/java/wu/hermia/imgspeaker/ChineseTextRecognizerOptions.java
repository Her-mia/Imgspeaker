package wu.hermia.imgspeaker;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.Objects;
import com.google.mlkit.vision.text.TextRecognizerOptionsInterface;
import com.google.mlkit.vision.text.TextRecognizerOptionsInterface.LanguageOption;
import com.google.mlkit.vision.text.internal.TextRecognizerOptionsUtils;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

public final class ChineseTextRecognizerOptions implements TextRecognizerOptionsInterface {
    @VisibleForTesting
    final AtomicReference zza = new AtomicReference();
    @Nullable
    private final Executor zzb;

    public final int getLoggingEventId() {
        return this.getIsThickClient() ? 24316 : 24330;
    }

    @LanguageOption
    public final int getLoggingLanguageOption() {
        return 2;
    }

    public int hashCode() {
        Executor var1 = this.zzb;
        return Objects.hashCode(new Object[]{var1});
    }

    @NonNull
    public final String getConfigLabel() {
        return "taser_tflite_gocrchinese_and_latin_mbv2_aksara_layout_gcn_mobile";
    }

    @NonNull
    public final String getLanguageHint() {
        return "zh";
    }

    @NonNull
    public final String getLoggingLibraryName() {
        return !this.getIsThickClient() ? "play-services-mlkit-text-recognition-chinese" : "text-recognition-chinese";
    }

    @NonNull
    public final String getLoggingLibraryNameForOptionalModule() {
        return "optional-module-text-chinese";
    }

    @NonNull
    public final String getModuleId() {
        return !this.getIsThickClient() ? "com.google.android.gms.mlkit_ocr_chinese" : "com.google.mlkit.dynamite.text.chinese";
    }

    @Nullable
    public final Executor getExecutor() {
        return this.zzb;
    }

    public boolean equals(@Nullable Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ChineseTextRecognizerOptions)) {
            return false;
        } else {
            o = (ChineseTextRecognizerOptions)o;
            return Objects.equal(this.zzb, ((ChineseTextRecognizerOptions)o).zzb);
        }
    }

    public final boolean getIsThickClient() {
        return TextRecognizerOptionsUtils.isThickClient(this.zza, "com.google.mlkit.dynamite.text.chinese");
    }

    public static class Builder {
        @Nullable
        private Executor zza;

        @NonNull
        public Builder setExecutor(@NonNull Executor executor) {
            this.zza = executor;
            return this;
        }

        @NonNull
        public ChineseTextRecognizerOptions build() {
            return new ChineseTextRecognizerOptions(this.zza, (zza)null);
        }

        public Builder() {
        }
    }
}
