package com.bemyapp.memocard;

import java.util.ArrayList;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;

import com.bemyapp.memocard.R;
import com.bemyapp.memocard.cheesegridadapter.CheeseDynamicAdapter;
import com.bemyapp.memocard.cheesegridadapter.CustomDynamicGridView;
import com.bemyapp.memocard.cheesegridadapter.IGameNotify;
import com.bemyapp.memocard.dialog.ConfirmationDialog;
import com.bemyapp.memocard.dialog.CounterDialog;
import com.bemyapp.memocard.dialog.IClickCustomListener;
import com.bemyapp.memocard.dialog.InformationDialog;
import com.bemyapp.memocard.entity.Card;
import com.bemyapp.memocard.entity.Joker;
import com.bemyapp.memocard.filp3d.DisplayNextView;
import com.bemyapp.memocard.filp3d.Flip3dAnimation;
import com.bemyapp.memocard.util.DensityHelper;
import com.bemyapp.memocard.util.MVManager;
import com.bemyapp.memocard.util.MVMediaPlayer;
import com.bemyapp.memocard.util.SystemUiHider;
/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
@SuppressWarnings("deprecation")
public class GameActivity extends Activity implements IClickCustomListener, IGameNotify{

	static final String TAG = GameActivity.class.getSimpleName();
	public Gallery galleryImage;
	
	private static int start_timeout = 20000; 
	
	private ImageView joker, img_edit;
	private Button btn_go;
	private SeekBar seekBar;
	private RelativeLayout palette_layout;
	
	private CustomDynamicGridView grid_show;
	private CustomDynamicGridView grid_check;
	private CheeseDynamicAdapter adapter_show; 
	private CheeseDynamicAdapter adapter_check;
	private ArrayList<Card> list_show = new ArrayList<Card>(), 
							list_check = new ArrayList<Card>();

	private int levelId = 6;
	private int nextCard = -1,
				nextCheck = -1, 
				posToUpdate = -1;
	
	private boolean isGameStarted = false,
					isJokerUsed = false;
	
	private Time start_check;
	private InformationDialog resultDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.game_layout);
		
		galleryImage = (Gallery) findViewById(R.id.gallery1);
		galleryImage.setSpacing(2);
		galleryImage.setAdapter(new GalleryImageAdapter(this, MVManager.getInstance().getListeCarte()));
		galleryImage.setVisibility(View.GONE);
		
		galleryImage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				if(isGameStarted)
				{	
					int pos = posToUpdate;
					if(pos == -1)
						pos = ++nextCard;
					
					insertCheckedCard(pos, MVManager.getInstance().getListeCarte().get(position));
				}
			}
			
		});
		
		palette_layout = (RelativeLayout) findViewById(R.id.palette_layout);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, DensityHelper.getInstance(this).getScaledHeight(120));
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		palette_layout.setLayoutParams(params);
				
		grid_show 	= (CustomDynamicGridView) findViewById(R.id.imagesShow);
		grid_check 	= (CustomDynamicGridView) findViewById(R.id.imageDetect);
		
		grid_show.setSelector(android.R.color.transparent);
		grid_check.setSelector(android.R.color.transparent);
				
		try{
			levelId = getIntent().getExtras().getInt("level");
			start_timeout = getIntent().getExtras().getInt("time");
		}catch(Exception e){}
		
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		final int max = start_timeout/1000;
		seekBar.setMax(max);
		seekBar.setProgress(max);
		seekBar.setEnabled(false);
		
		list_show.addAll(MVManager.getInstance().getList(levelId));
		adapter_show = new CheeseDynamicAdapter(this, list_show, levelId, this);
		grid_show.setAdapter(adapter_show);
		
		list_check.addAll(MVManager.getInstance().getDefaultList(levelId));
		adapter_check = new CheeseDynamicAdapter(this, list_check, levelId, this);
		grid_check.setAdapter(adapter_check);
		
		grid_show.setNumColumns(levelId);
		grid_check.setNumColumns(levelId);
		
		grid_check.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            	if(isGameStarted){
            		if(posToUpdate == -1)
            		{	
            			ImageView img = (ImageView) view.findViewById(R.id.imageView1);
            			applyRotation(0, 180, img, R.drawable.renverser);

            			posToUpdate = position;
            		} else if(posToUpdate == position)
            			initEditSimpleCard(position, view);
            	}
                
                return false;
            }
        });
		
		grid_check.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            	if(posToUpdate > -1 && isGameStarted)
            		initEditSimpleCard(posToUpdate, grid_check.getChildAt(posToUpdate));
            	
            }
        });
		
		final Thread startThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				// Start seek bar animation
				for (int i = 0; i < max; i++) {
					try{
						Thread.sleep(1000);
					}catch(Exception e){}
					
					GameActivity.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							int current = seekBar.getProgress();
							seekBar.setProgress(current-1);
						}
					});
					
				}
				
				// Get start check time
				start_check = new Time(Time.getCurrentTimezone());
				start_check.setToNow();
				
				GameActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
						// Hide grid_show cards
						toggleShowCards(true);
						isGameStarted = true;
						
					}
				});
				
			}
		});
		
		btn_go = (Button) findViewById(R.id.btn_go);
		btn_go.setClickable(false);
		btn_go.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isGameStarted)
				{
					// Start checking...
					startChecking();
				
					galleryImage.setVisibility(View.GONE);
					btn_go.setBackgroundResource(R.drawable.btn_red);
					btn_go.setClickable(false);
				}
				
			}
		});
		
		img_edit = (ImageView) findViewById(R.id.img_edit);
		img_edit.setEnabled(false);
		img_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isGameStarted){
					if(!grid_check.isEditMode()){
						grid_check.startEditMode();
						img_edit.setImageResource(R.drawable.icon_edit_checked);
					}
					else {
						grid_check.stopEditMode();
						img_edit.setImageResource(R.drawable.icon_edit_normal);
					}
				}
			}
		});	
		
		joker = (ImageView) findViewById(R.id.joker);
		joker.setEnabled(false);
		joker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(isGameStarted && !isJokerUsed)
				{
					int pos = posToUpdate;
					if(pos == -1)
						pos = ++nextCard;
					
					isJokerUsed = insertCheckedCard(pos, new Joker());
					if(isJokerUsed)
						joker.setImageResource(R.drawable.icon_joker_checked);
					
				}
			}
		});
		
		CounterDialog c = new CounterDialog(this);
		c.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface arg0) {
				startThread.start();
			}
		});
		c.show();
		
		MVMediaPlayer.getInstance(this).initBackgroundSound();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(isGameStarted)
			MVMediaPlayer.getInstance(this).resumeBackgroundSound();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		MVMediaPlayer.getInstance(this).pauseBackgroundSound();
	}
	
	private void toggleShowCards(boolean state){
		hideAll();
		
		galleryImage.setVisibility(state?View.VISIBLE:View.GONE);
		img_edit.setEnabled(state);
		joker.setEnabled(state);
		btn_go.setClickable(state);

	}
	
	private void initEditSimpleCard(int position, View view){
		Card c = list_check.get(position);
		ImageView img = (ImageView) view.findViewById(R.id.imageView1);
		applyRotation(0, 180, img, c.getImage());

		posToUpdate = -1;
	}
	
	private boolean insertCheckedCard(int position, Card card){
		if(position < list_check.size()){
			
			if(isJoker(position)){
				isJokerUsed = false;
				joker.setImageResource(R.drawable.icon_joker_normal);
			}

			card.setToShow(true);
			list_check.set(position, card);

			View v = grid_check.getChildAt(position);

			MVMediaPlayer.getInstance(this).playSingleCardFlip();

			ImageView img = (ImageView) v.findViewById(R.id.imageView1);
			applyRotation(0, 180, img, card.getImage());

			adapter_check.set(list_check);

			if(position == list_check.size() - 1 && posToUpdate == -1){
//				galleryImage.setVisibility(View.GONE);
				btn_go.setBackgroundResource(R.drawable.btn_blue);

			}
			
			if(posToUpdate > -1)
				posToUpdate = -1;
			
			return true;
//			Log.i(TAG, "to insert At position " + position);
		}
		
		return false;
	}
	
	private boolean isJoker(int position){
		Card c = list_check.get(position);
		return c.getId() == 100;
	}
	
	private void startChecking(){
		btn_go.setEnabled(false);
		
		if (grid_check.isEditMode()) {
			grid_check.stopEditMode();
			img_edit.setImageResource(R.drawable.icon_edit_checked);
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				boolean isOK = true;
				nextCheck = -1;
				
				while(isOK && nextCheck < list_show.size()-1){
					nextCheck += 1;
					final Card cardShow = list_show.get(nextCheck);
	
					GameActivity.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {							
							View v = grid_show.getChildAt(nextCheck);
							
							ImageView img = (ImageView) v.findViewById(R.id.imageView1);
							applyRotation(0, 180, img, cardShow.getImage());
							
							list_show.get(nextCheck).setToShow(true);
						}
					});
					
					try{
						Thread.sleep(2000);
					}catch(Exception e){}
					
					Card cardCheck = list_check.get(nextCheck);
					if(cardCheck.getId() != cardShow.getId() && cardCheck.getId() != 100)
						isOK = false;
					
				}
				
				final boolean result = isOK;
				GameActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
						MVMediaPlayer.getInstance(GameActivity.this).stopBackgroundSound();
						
						if(result){
							// WIN
							MVMediaPlayer.getInstance(GameActivity.this).playSuccess();
							resultDialog = new InformationDialog(GameActivity.this, R.style.CustomDialogTheme, getString(R.string.app_name), getString(R.string.win), "");
						}else{
							//LOOSE
							MVMediaPlayer.getInstance(GameActivity.this).playFail();
							resultDialog = new InformationDialog(GameActivity.this, R.style.CustomDialogTheme, getString(R.string.app_name), getString(R.string.lost), "");
						}
						resultDialog.show();
						resultDialog.setOnDismissListener(new OnDismissListener() {
							
							@Override
							public void onDismiss(DialogInterface dialog) {
								gotoMenu();
							}
						});
					}
				});
				
			}
		}).start();
		
		
	}

	private void applyRotation(float start, float end , ImageView image1, int newImageRsc) {

		// Find the center of image
		final float centerX = image1.getWidth() / 2.0f;
		final float centerY = image1.getHeight() / 2.0f;

		final Flip3dAnimation rotation = new Flip3dAnimation(start, end, centerX, centerY);
		rotation.setDuration(1500);
		rotation.setFillAfter(true);

		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView(true, image1, image1, newImageRsc));

		image1.startAnimation(rotation);

	}
	
	public void hideAll(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				GameActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						MVMediaPlayer.getInstance(GameActivity.this).playCardsFlipSound();
					}
				});
				
				for(int i=0; i < list_show.size(); i++){
					list_show.get(i).setToShow(false);
					final View v = grid_show.getChildAt(i);
					
					GameActivity.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
														
							ImageView img = (ImageView) v.findViewById(R.id.imageView1);
							applyRotation(0, 180, img, R.drawable.renverser);
							
						}
					});
					
					try{
						Thread.sleep(200);
					}catch(Exception e){}
				}
				
				GameActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						MVMediaPlayer.getInstance(GameActivity.this).resumeBackgroundSound();
					}
				});
			}
		}).start();

	}
	
	
	ConfirmationDialog exitDialog;
	@Override
	public void onBackPressed() {
		exitDialog = new ConfirmationDialog(this, R.style.CustomDialogTheme, getString(R.string.app_name), getString(R.string.exit), "", this);
		exitDialog.setCancelable(false);
		exitDialog.show();
	}

	@Override
	public void onClickYes() {
		
		MVMediaPlayer.getInstance(this).stopBackgroundSound();
		
		list_show.clear();
		list_check.clear();
		finish();
		System.exit(0);
		
	}

	@Override
	public void onClickNo() {
		
		exitDialog.dismiss();
				
	}
	
	private void gotoMenu(){
		MVMediaPlayer.getInstance(this).stopBackgroundSound();
		
		startActivity(new Intent(GameActivity.this, MenuActivity.class));
		overridePendingTransition(R.anim.left_in, R.anim.left_out);
		finish();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void requestNotify(ArrayList list) {
		list_check.clear();
		list_check.addAll(list);
		adapter_check.notifyDataSetChanged();
	}
}
