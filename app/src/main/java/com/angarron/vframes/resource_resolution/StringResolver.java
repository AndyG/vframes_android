package com.angarron.vframes.resource_resolution;

import com.angarron.vframes.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andy on 12/23/15
 */
public class StringResolver {

    private static Map<String, Integer> stringIdMap;

    static {
        stringIdMap = new HashMap<>();

        //General Movelist
        stringIdMap.put("id_close_to_opponent", R.string.close_to_opponent);
        stringIdMap.put("id_can_be_done_in_air", R.string.can_be_done_in_air);
        stringIdMap.put("id_during_jump", R.string.during_jump);
        stringIdMap.put("id_during_jump_near_wall", R.string.during_jump_near_wall);
        stringIdMap.put("id_during_forward_jump", R.string.during_forward_jump);
        stringIdMap.put("id_during_guard", R.string.during_guard);
        stringIdMap.put("id_hold_buttons_to_attack", R.string.hold_buttons_to_attack);
        stringIdMap.put("id_close_to_grounded_opponent", R.string.close_to_grounded_opponent);
        stringIdMap.put("id_close_to_airborne_opponent", R.string.close_to_airborne_opponent);
        stringIdMap.put("id_no_input", R.string.no_input);
        stringIdMap.put("id_hold_button", R.string.hold_button);
        stringIdMap.put("id_during_vertical_or_forward_jump", R.string.during_vertical_or_forward_jump);
        stringIdMap.put("id_during_vtrigger", R.string.during_vtrigger);
        stringIdMap.put("id_hold_and_release", R.string.hold_and_release);
        stringIdMap.put("id_close_to_crouching_opponent", R.string.close_to_crouching_opponent);
        stringIdMap.put("id_during_run", R.string.during_run);

        //Karin Movelist
        stringIdMap.put("id_karin_tenko_posttext", R.string.karin_tenko_posttext);
        stringIdMap.put("id_karin_meioken_posttext", R.string.karin_meioken_posttext);

        //Dhalsim Movelist
        stringIdMap.put("id_dhalsim_gale_pretext", R.string.during_vertical_or_forward_jump);

        //Claw Movelist
        stringIdMap.put("id_claw_claw_on_only", R.string.claw_claw_on_only);
        stringIdMap.put("id_claw_claw_off_only", R.string.claw_claw_off_only);
        stringIdMap.put("id_claw_switch", R.string.claw_switch);

        //Chun Movelist
        stringIdMap.put("id_up_to_three_times", R.string.chun_up_to_three_times);

        //Ken Movelist
        stringIdMap.put("id_ken_step_kick_hold_buttons", R.string.ken_step_kick_hold_buttons);

        //Zangief Movelist
        stringIdMap.put("id_hold_buttons_for_special_lariat", R.string.zangief_hold_buttons_for_special_lariat);
        stringIdMap.put("id_can_move_forward_while_holding_buttons", R.string.zangief_can_move_forward_while_holding_buttons);

        //Dictator Movelist
        stringIdMap.put("id_after_head_press", R.string.dictator_after_head_press);
        stringIdMap.put("id_before_head_press", R.string.dictator_before_head_press);
        stringIdMap.put("id_pyscho_reflect_hold_buttons", R.string.dictator_pyscho_reflect_hold_buttons);

        //Laura Movelist
        stringIdMap.put("id_laura_hold_buttons_to_power_up", R.string.laura_hold_buttons_to_power_up);

        //Birdie Movelist
        stringIdMap.put("id_birdie_hanging_chain_posttext", R.string.birdie_hanging_chain_posttext);

        //Mika Movelist
        stringIdMap.put("id_mika_vskill_increases_power", R.string.mika_vskill_increases_power);
        stringIdMap.put("id_mika_hold_buttons_to_power_up_throws", R.string.mika_hold_buttons_to_power_up_throws);

        //Rashid Movelist
        stringIdMap.put("id_rashid_repeat_buttons_to_extend", R.string.rashid_repeat_buttons_to_extend);

        //Nash Movelist
        stringIdMap.put("id_nash_press_for_second_boom", R.string.nash_press_for_second_boom);

        //Necalli Movelist
        stringIdMap.put("id_necalli_vskill_control_distance", R.string.necalli_vskill_control_distance);

    }

    public static int getStringId(String key) {
        if (stringIdMap.containsKey(key)) {
            return stringIdMap.get(key);
        } else {
            throw new RuntimeException("could not find key: " + key);
        }
    }
}
