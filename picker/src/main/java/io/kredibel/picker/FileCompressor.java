package io.kredibel.picker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class FileCompressor {
    //max width and height values of the compressed image is taken as 612x816
    private final Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private final String destinationDirectoryPath;

    public FileCompressor(Context context) {
        destinationDirectoryPath = context.getCacheDir().getPath() + File.separator + "images";
    }

    /*public static Bitmap getBitmapData(Context context, Intent data) {
        Uri photoUri = null;
        if (data != null && data.getData() != null) {
            photoUri = data.getData();
        }

        String path = photoUri.getPath();

        try {
            InputStream imageStream = context.getContentResolver().openInputStream(photoUri);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            imageStream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/

    static File compressImage(File imageFile, Bitmap.CompressFormat compressFormat, int quality, String destinationPath) throws IOException {
        FileOutputStream fileOutputStream = null;
        File file = new File(destinationPath).getParentFile();
        if (!Objects.requireNonNull(file).exists()) {
            file.mkdirs();
        }
        try {
            fileOutputStream = new FileOutputStream(destinationPath);
            // write the compressed bitmap at the destination specified by destinationPath.
            decodeSampledBitmapFromFile(imageFile).compress(compressFormat, quality, fileOutputStream);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return new File(destinationPath);
    }

    static Bitmap decodeSampledBitmapFromFile(File imageFile) throws IOException {
        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap scaledBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        //check the rotation of the image and display it properly
        ExifInterface exif;
        exif = new ExifInterface(imageFile.getAbsolutePath());
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
        Matrix matrix = new Matrix();
        if (orientation == 6) {
            matrix.postRotate(90);
        } else if (orientation == 3) {
            matrix.postRotate(180);
        } else if (orientation == 8) {
            matrix.postRotate(270);
        }
        scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        return scaledBitmap;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > 816 || width > 612) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= 816 && (halfWidth / inSampleSize) >= 612) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /*public FileCompressor setDestinationDirectoryPath(String destinationDirectoryPath) {
        this.destinationDirectoryPath = destinationDirectoryPath;
        return this;
    }*/

    public File compressToFile(File imageFile) throws IOException {
        return compressToFile(imageFile, imageFile.getName());
    }

    public File compressToFile(File imageFile, String compressedFileName) throws IOException {
        int quality = 50;
        return compressImage(imageFile, compressFormat, quality, destinationDirectoryPath + File.separator + compressedFileName);
    }

    /*public Bitmap compressToBitmap(File imageFile) throws IOException {
        return decodeSampledBitmapFromFile(imageFile);
    }*/
}