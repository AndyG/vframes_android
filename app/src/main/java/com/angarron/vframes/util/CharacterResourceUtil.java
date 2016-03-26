package com.angarron.vframes.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;

import com.angarron.vframes.R;

import data.model.CharacterID;

public class CharacterResourceUtil {

    public static String getCharacterDisplayName(Context context, CharacterID characterID) {
        return context.getString(getNameResource(characterID));
    }

    public static ColorDrawable getCharacterPrimaryColorDrawable(Context context, CharacterID characterID) {
        switch(characterID) {
            case ALEX:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.alex_primary));
            case RYU:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.ryu_primary));
            case CHUN:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.chun_primary));
            case DICTATOR:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.dictator_primary));
            case BIRDIE:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.birdie_primary));
            case NASH:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.nash_primary));
            case CAMMY:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.cammy_primary));
            case KEN:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.ken_primary));
            case MIKA:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.mika_primary));
            case NECALLI:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.necalli_primary));
            case CLAW:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.claw_primary));
            case RASHID:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.rashid_primary));
            case KARIN:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.karin_primary));
            case LAURA:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.laura_primary));
            case DHALSIM:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.dhalsim_primary));
            case ZANGIEF:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.zangief_primary));
            case FANG:
                return new ColorDrawable(ContextCompat.getColor(context, R.color.fang_primary));
            default:
                throw new RuntimeException("unable to resolve character accent color drawable: " + characterID);
        }
    }

    private static int getNameResource(CharacterID characterID) {
        switch(characterID) {
            case ALEX:
                return R.string.alex_name;
            case RYU:
                return R.string.ryu_name;
            case CHUN:
                return R.string.chun_name;
            case DICTATOR:
                return R.string.dictator_name;
            case BIRDIE:
                return R.string.birdie_name;
            case NASH:
                return R.string.nash_name;
            case CAMMY:
                return R.string.cammy_name;
            case KEN:
                return R.string.ken_name;
            case MIKA:
                return R.string.mika_name;
            case NECALLI:
                return R.string.necalli_name;
            case CLAW:
                return R.string.claw_name;
            case RASHID:
                return R.string.rashid_name;
            case KARIN:
                return R.string.karin_name;
            case LAURA:
                return R.string.laura_name;
            case DHALSIM:
                return R.string.dhalsim_name;
            case ZANGIEF:
                return R.string.zangief_name;
            case FANG:
                return R.string.fang_name;
            default:
                throw new RuntimeException("unable to resolve character name: " + characterID);
        }
    }

    public static int getCharacterBannerResource(CharacterID targetCharacter) {
        switch(targetCharacter) {
            case ALEX:
                return R.drawable.alex_card;
            case RYU:
                return R.drawable.ryu_card;
            case CHUN:
                return R.drawable.chun_card;
            case DICTATOR:
                return R.drawable.dictator_card;
            case BIRDIE:
                return R.drawable.birdie_card;
            case NASH:
                return R.drawable.nash_card;
            case CAMMY:
                return R.drawable.cammy_card;
            case KEN:
                return R.drawable.ken_card;
            case MIKA:
                return R.drawable.mika_card;
            case NECALLI:
                return R.drawable.necalli_card;
            case CLAW:
                return R.drawable.claw_card;
            case RASHID:
                return R.drawable.rashid_card;
            case KARIN:
                return R.drawable.karin_card;
            case LAURA:
                return R.drawable.laura_card;
            case DHALSIM:
                return R.drawable.dhalsim_card;
            case ZANGIEF:
                return R.drawable.zangief_card;
            case FANG:
                return R.drawable.fang_card;
            default:
                throw new RuntimeException("unable to resolve character drawable: " + targetCharacter);
        }
    }
}
