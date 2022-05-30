package io.kredibel.picker;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Compressor {
    //max width and height values of the compressed image is taken as 612x816
    private int maxWidth = 612;
    private int maxHeight = 816;
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private int quality = 50;
    private String destinationDirectoryPath;

    private Context ctx;

    public Compressor(Context context) {
        this.ctx = context;
        destinationDirectoryPath = context.getCacheDir().getPath() + File.separator + "images";
    }

    public static Bitmap getBitmapData(Context context, Intent data) {
        Uri photoUri = null;
        if (data != null && data.getData() != null) {
            photoUri = data.getData();
        }

        //String path = photoUri.getPath();

        try {
            InputStream imageStream = context.getContentResolver().openInputStream(photoUri);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            imageStream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static File compressImage(File imageFile, int reqWidth, int reqHeight, Bitmap.CompressFormat compressFormat, int quality, String destinationPath) throws IOException {
        FileOutputStream fileOutputStream = null;
        File file = new File(destinationPath).getParentFile();
        if (!Objects.requireNonNull(file).exists()) {
            file.mkdirs();
        }
        try {
            fileOutputStream = new FileOutputStream(destinationPath);
            // write the compressed bitmap at the destination specified by destinationPath.
            decodeSampledBitmapFromFile(imageFile, reqWidth, reqHeight).compress(compressFormat, quality, fileOutputStream);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return new File(destinationPath);
    }

    static Bitmap decodeSampledBitmapFromFile(File imageFile, int reqWidth, int reqHeight) throws IOException {
        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
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

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = ctx.getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Compressor setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public Compressor setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public Compressor setCompressFormat(Bitmap.CompressFormat compressFormat) {
        this.compressFormat = compressFormat;
        return this;
    }

    public Compressor setQuality(int quality) {
        this.quality = quality;
        return this;
    }

    /*public Flowable<File> compressToFileAsFlowable(final File imageFile) {
        return compressToFileAsFlowable(imageFile, imageFile.getName());
    }
    public Flowable<File> compressToFileAsFlowable(final File imageFile,
                                                   final String compressedFileName) {
        return Flowable.defer((Callable<Flowable<File>>) () -> {
            try {
                return Flowable.just(compressToFile(imageFile, compressedFileName));
            } catch (IOException e) {
                return Flowable.error(e);
            }
        });
    }
    public Flowable<Bitmap> compressToBitmapAsFlowable(final File imageFile) {
        return Flowable.defer((Callable<Flowable<Bitmap>>) () -> {
            try {
                return Flowable.just(compressToBitmap(imageFile));
            } catch (IOException e) {
                return Flowable.error(e);
            }
        });
    }*/

    public Compressor setDestinationDirectoryPath(String destinationDirectoryPath) {
        this.destinationDirectoryPath = destinationDirectoryPath;
        return this;
    }

    public File compressUritoFile(Uri uri) throws IOException {
        return compressToFile(new File(getRealPathFromUri(uri)));
    }

    public File compressToFile(File imageFile) throws IOException {
        return compressToFile(imageFile, imageFile.getName());
    }

    public File compressToFile(File imageFile, String compressedFileName) throws IOException {
        return compressImage(imageFile, maxWidth, maxHeight, compressFormat, quality, destinationDirectoryPath + File.separator + compressedFileName);
    }

    public Bitmap compressToBitmap(File imageFile) throws IOException {
        return decodeSampledBitmapFromFile(imageFile, maxWidth, maxHeight);
    }
}
