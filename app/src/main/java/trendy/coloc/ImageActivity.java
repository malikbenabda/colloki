package trendy.coloc;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import trendy.coloc.entities.Annonce;
import trendy.coloc.entities.Image;
import trendy.coloc.tools.AnnonceTools;

public class ImageActivity extends AppCompatActivity {

    // this is the action code we use in our intent,
    // this way we know we're looking at the response from our own action
    private static final int SELECT_SINGLE_PICTURE = 101;

    private static final int SELECT_MULTIPLE_PICTURE = 201;

    public static final String IMAGE_TYPE = "image/*";

    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageViewCover;
    private RelativeLayout detailsImagesRL;
    private Button btnValidate;
    private static ArrayList<com.nguyenhoanglam.imagepicker.model.Image> images;
    private Annonce annonce;
    private Image imageEntity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annonce_images_edit);
        btnValidate = (Button) findViewById(R.id.validateImagesAdd);
        imageView1 = (ImageView) findViewById(R.id.annonceDetailImage1);
        imageView2 = (ImageView) findViewById(R.id.annonceDetailImage2);
        imageView3 = (ImageView) findViewById(R.id.annonceDetailImage3);

        imageView4 = (ImageView) findViewById(R.id.annonceDetailImage4);
        imageView5 = (ImageView) findViewById(R.id.annonceDetailImage5);
        imageViewCover = (ImageView) findViewById(R.id.imageCoverEdit);
        detailsImagesRL = (RelativeLayout) findViewById(R.id.detailsImagesRL);

        Picasso.with(ImageActivity.this).load(imageEntity.getUrl());

        //get annonces and put them in views same for other 5 images of details
        ArrayList<trendy.coloc.entities.Image> imagelist = Image.getAllByIdAnnonce(AnnonceTools.tempAnnonce.getId(), ImageActivity.this);
        if (!(imagelist.isEmpty() || imagelist == null) && imagelist.size() > 4) { //fillup
            //if its cover set it to cover else add it to image next detail
            if (imagelist.get(0).isCover()) {
                Picasso.with(ImageActivity.this).load(imagelist.get(0).getUrl()).into(imageViewCover);
            } else { //put it in imageview 1
                Picasso.with(ImageActivity.this).load(imagelist.get(0).getUrl()).into(imageView1);
            }

            //if its cover set it to cover else add it to image next detail
            if (imagelist.get(1).isCover()) {
                Picasso.with(ImageActivity.this).load(imagelist.get(1).getUrl()).into(imageViewCover);
            } else { //put it in imageview 2
                Picasso.with(ImageActivity.this).load(imagelist.get(1).getUrl()).into(imageView2);
            }


            //if its cover set it to cover else add it to image next detail
            if (imagelist.get(2).isCover()) {
                Picasso.with(ImageActivity.this).load(imagelist.get(2).getUrl()).into(imageViewCover);
            } else { //put it in imageview 3
                Picasso.with(ImageActivity.this).load(imagelist.get(2).getUrl()).into(imageView3);
            }


            //if its cover set it to cover else add it to image next detail
            if (imagelist.get(3).isCover()) {
                Picasso.with(ImageActivity.this).load(imagelist.get(3).getUrl()).into(imageViewCover);
            } else { //put it in imageview 4
                Picasso.with(ImageActivity.this).load(imagelist.get(3).getUrl()).into(imageView4);
            }


            //if its cover set it to cover else add it to image next detail
            if (imagelist.get(4).isCover()) {
                Picasso.with(ImageActivity.this).load(imagelist.get(4).getUrl()).into(imageViewCover);
            } else { //put it in imageview 5
                Picasso.with(ImageActivity.this).load(imagelist.get(4).getUrl()).into(imageView5);
            }


        }


        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //verification des entrées des images


            }
        });
        imageViewCover.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
                Intent intent = new Intent();
                intent.setType(IMAGE_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        getString(R.string.select_picture)), SELECT_SINGLE_PICTURE);
                return false;
            }
        });
        // multiple image selection
        detailsImagesRL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ImagePicker.create(ImageActivity.this).multi()
                        .multi() // multi mode (default mode)
                        .limit(5) // max images can be selected
                        .showCamera(false) // show camera or not (true by default)
                        .start(SELECT_MULTIPLE_PICTURE); // start image picker activity with request code

                return false;
            }

        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_SINGLE_PICTURE) {

                Uri selectedImageUri = data.getData();
                String selectedImagePath = getPath(selectedImageUri);
                //upload image withe selectedImagepath

                //upload image to ftp  returns url to newCoverURL
                //fix later
                String newCoverURL = "http://www.homedesignideasplans.com/wp-content/uploads/2015/08/2-bedroom-house_2.jpg";
                Picasso.with(ImageActivity.this).load(newCoverURL).into(imageViewCover);
            }
            if (requestCode == SELECT_MULTIPLE_PICTURE && data != null) {
                images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
                try {
                    Bitmap myBitmap = null;
                    myBitmap = BitmapFactory.decodeFile(images.get(0).getPath());
                    imageView1.setImageBitmap(myBitmap);

                    myBitmap = BitmapFactory.decodeFile(images.get(1).getPath());
                    imageView2.setImageBitmap(myBitmap);

                    myBitmap = BitmapFactory.decodeFile(images.get(2).getPath());
                    imageView3.setImageBitmap(myBitmap);

                    myBitmap = BitmapFactory.decodeFile(images.get(3).getPath());
                    imageView4.setImageBitmap(myBitmap);

                    myBitmap = BitmapFactory.decodeFile(images.get(4).getPath());
                    imageView5.setImageBitmap(myBitmap);
                } finally {

                }
            }
        } else {
            // report failure
            Toast.makeText(getApplicationContext(), R.string.msg_failed_to_get_intent_data, Toast.LENGTH_LONG).show();
            Log.d(ImageActivity.class.getSimpleName(), "Failed to get intent data, result code is " + resultCode);
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {

        // just some safety built in
        if (uri == null) {
            // perform some logging or show user feedback
            Toast.makeText(getApplicationContext(), R.string.msg_failed_to_get_picture, Toast.LENGTH_LONG).show();
            Log.d(ImageActivity.class.getSimpleName(), "Failed to parse image path from image URI " + uri);
            return null;
        }

        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here, thanks to the answer from @mad indicating this is needed for
        // working code based on images selected using other file managers
        return uri.getPath();
    }


//    /**
//     * helper to scale down image before display to prevent render errors:
//     * "Bitmap too large to be uploaded into a texture"
//     */
//    private void displayPicture(String imagePath, ImageView imageView) {
//
//        // from http://stackoverflow.com/questions/22633638/prevent-bitmap-too-large-to-be-uploaded-into-a-texture-android
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 4;
//
//        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//        int height = bitmap.getHeight(), width = bitmap.getWidth();
//
//        if (height > 1280 && width > 960){
//            Bitmap imgbitmap = BitmapFactory.decodeFile(imagePath, options);
//            imageView.setImageBitmap(imgbitmap);
//        } else {
//            imageView.setImageBitmap(bitmap);
//        }
//    }


}
