package com.classy.heatmaplibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HeatMap {


    private static double[][] normalizeMatrix(double[][] matrix) {
        double RT = 10.0; // Ratio
        double[][] lowResMatrix = new double[(int) (matrix.length / RT)][(int) (matrix[0].length / RT)];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                try {
                    lowResMatrix[(int) (i / RT)][(int) (j / RT)] += matrix[i][j];
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
            }
        }


        double[][] norMatrix = new double[lowResMatrix.length][lowResMatrix[0].length];

        int R = 10; // Range
        for (int y = 0; y < lowResMatrix.length; y++) {
            Log.d("pttt", y + " / " + lowResMatrix.length);
            for (int x = 0; x < lowResMatrix[0].length; x++) {
                for (int i = -R; i <= R; i++) {
                    for (int j = -R; j <= R; j++) {
                        try {
                            norMatrix[y][x] = lowResMatrix[y + i][x + j];
                        } catch (ArrayIndexOutOfBoundsException ex) {
                        }
                    }
                }
            }
        }


        double[][] clrMatrix = new double[220][220];

        for (int i = 0; i < 220; i++) {
            for (int j = 0; j < 220; j++) {
                clrMatrix[i][j] = j / 220.0;
            }
        }
        return norMatrix;
    }


    /**
     * this function prepare heat map bitmap.
     * @param matrix double matrix, must be max 1.0 value
     * @param callBack_heatMap to return after long task.
     */
    public static void generateBitmap(double[][] matrix, CallBack_HeatMap callBack_heatMap) {
        new MyLongOperation(matrix, callBack_heatMap).execute();
    }

    private static Bitmap generateBitmap(double[][] matrix) {
        matrix = normalizeMatrix(matrix);
        int HEIGHT = matrix.length;
        int WIDTH = matrix[0].length;
        Bitmap image = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);

        // https://stackoverflow.com/a/52498075/7147289

        int column = 0;
        int row = 0;
        double val;
        while (row < HEIGHT) {
            while (column < WIDTH) {
                val = matrix[row][column];
                //image.setPixel(column, row, colors[(int) val]);
                image.setPixel(column, row, Color.HSVToColor(new float[]{(float) (220f - 220.0 * val), 1.0f, 1.0f}));
                column++;
            }
            row++;
            column = 0;
        }

        return image;
    }

    public interface CallBack_HeatMap {
        void bitmapReady(Bitmap bitmap);
    }

    static class MyLongOperation extends AsyncTask<Void, Void, Void> {

        private double[][] matrix;
        private CallBack_HeatMap callBack_heatMap;

        public MyLongOperation(double[][] matrix, CallBack_HeatMap callBack_heatMap) {
            this.matrix = matrix;
            this.callBack_heatMap = callBack_heatMap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Bitmap bitmap = generateBitmap(matrix);
            if (callBack_heatMap != null) {
                callBack_heatMap.bitmapReady(bitmap);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }


    public static class TaskRunner {
        private final Executor executor = Executors.newSingleThreadExecutor(); // change according to your requirements
        private final Handler handler = new Handler(Looper.getMainLooper());

        public interface Callback<R> {
            void onComplete(R result);
        }

        public <R> void executeAsync(Callable<R> callable, Callback<R> callback) {
            executor.execute(() -> {
                final R result;
                try {
                    result = callable.call();
                    handler.post(() -> {
                        callback.onComplete(result);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
