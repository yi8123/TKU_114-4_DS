// 檔名：MediaProcessingSystem.java
interface Playable {
    void play();
}

interface Compressible {
    void compress(int qualityLevel);
}

abstract class MediaFile {
    private final String filename;
    private final int sizeKb;

    MediaFile(String filename, int sizeKb) {
        this.filename = (filename == null || filename.isBlank()) ? "unnamed.media" : filename;
        this.sizeKb = Math.max(0, sizeKb);
    }

    public String getFilename() {
        return filename;
    }

    public int getSizeKb() {
        return sizeKb;
    }

    abstract void showInfo();
}

class ImageFile extends MediaFile implements Compressible {
    ImageFile(String filename, int sizeKb) {
        super(filename, sizeKb);
    }

    @Override
    void showInfo() {
        System.out.println("[Image] " + getFilename() + " (" + getSizeKb() + " KB)");
    }

    @Override
    public void compress(int qualityLevel) {
        System.out.println("  -> Compressing image [" + getFilename() + "] to quality: " + qualityLevel + "%");
    }
}

class AudioFile extends MediaFile implements Playable, Compressible {
    AudioFile(String filename, int sizeKb) {
        super(filename, sizeKb);
    }

    @Override
    void showInfo() {
        System.out.println("[Audio] " + getFilename() + " (" + getSizeKb() + " KB)");
    }

    @Override
    public void play() {
        System.out.println("  -> Playing audio stream for: " + getFilename());
    }

    @Override
    public void compress(int qualityLevel) {
        System.out.println("  -> Compressing audio bitrate for: " + getFilename());
    }
}

class VideoFile extends MediaFile implements Playable {
    VideoFile(String filename, int sizeKb) {
        super(filename, sizeKb);
    }

    @Override
    void showInfo() {
        System.out.println("[Video] " + getFilename() + " (" + getSizeKb() + " KB)");
    }

    @Override
    public void play() {
        System.out.println("  -> Decoding & playing video: " + getFilename());
    }
}

public class MediaProcessingSystem {
    public static void main(String[] args) {
        MediaFile[] mediaList = {
            new ImageFile("banner.png", 2048),
            new AudioFile("podcast.mp3", 15360),
            new VideoFile("intro.mp4", 102400)
        };

        for (MediaFile media : mediaList) {
            media.showInfo();
            if (media instanceof Playable playable) {
                playable.play();
            }
            if (media instanceof Compressible compressible) {
                compressible.compress(80);
            }
            System.out.println();
        }
    }
}