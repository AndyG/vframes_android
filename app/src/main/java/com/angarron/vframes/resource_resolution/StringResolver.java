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
        stringIdMap.put("id_hold_button", R.string.hold_button);
        stringIdMap.put("id_during_vertical_or_forward_jump", R.string.during_vertical_or_forward_jump);
        stringIdMap.put("id_during_vtrigger", R.string.during_vtrigger);
        stringIdMap.put("id_hold_and_release", R.string.hold_and_release);
        stringIdMap.put("id_close_to_crouching_opponent", R.string.close_to_crouching_opponent);
        stringIdMap.put("id_during_run", R.string.during_run);

        //General Descriptions
        stringIdMap.put("id_hits_overhead", R.string.hits_overhead);

        //Ryu Movelist
        stringIdMap.put("id_ryu_shoryuken_description", R.string.ryu_shoryuken_description);
        stringIdMap.put("id_ryu_vtrigger_description", R.string.ryu_vtrigger_description);
        stringIdMap.put("id_ryu_vskill_description", R.string.ryu_vskill_description);

        //Karin Movelist
        stringIdMap.put("id_karin_tenko_posttext", R.string.karin_tenko_posttext);
        stringIdMap.put("id_karin_meioken_posttext", R.string.karin_meioken_posttext);
        stringIdMap.put("id_karin_vskill_description", R.string.karin_vskill_description);

        //Claw Movelist
        stringIdMap.put("id_claw_claw_on_only", R.string.claw_claw_on_only);
        stringIdMap.put("id_claw_claw_off_only", R.string.claw_claw_off_only);
        stringIdMap.put("id_claw_switch", R.string.claw_switch);

        //Chun Movelist
        stringIdMap.put("id_up_to_three_times", R.string.chun_up_to_three_times);
        stringIdMap.put("id_chun_vtrigger_description", R.string.chun_vtrigger_description);
        stringIdMap.put("id_chun_vskill_description", R.string.chun_vskill_description);
        stringIdMap.put("id_chun_senenshu_description", R.string.chun_senenshu_description);
        stringIdMap.put("id_chun_critical_art_description", R.string.chun_critical_art_description);

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
        stringIdMap.put("id_laura_after_mp_or_ex_bolt_charge", R.string.laura_after_mp_or_ex_bolt_charge);
        stringIdMap.put("id_laura_bolt_charge_description", R.string.laura_bolt_charge_description);
        stringIdMap.put("id_laura_split_river_description", R.string.laura_split_river_description);
        stringIdMap.put("id_laura_rodeo_break_description", R.string.laura_rodeo_break_description);
        stringIdMap.put("id_laura_vtrigger_description", R.string.laura_vtrigger_description);
        stringIdMap.put("id_laura_vskill_overhead_description", R.string.laura_vskill_overhead_description);
        stringIdMap.put("id_laura_vskill_avante_description", R.string.vskill_avante_description);
        stringIdMap.put("id_laura_vskill_esquiva_description", R.string.vskill_esquiva_description);

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
        stringIdMap.put("id_necalli_disc_guidance_description", R.string.necalli_disc_guidance_description);
        stringIdMap.put("id_necalli_raging_light_description", R.string.necalli_raging_light_description);
        stringIdMap.put("id_necalli_vtrigger_description", R.string.necalli_vtrigger_description);
        stringIdMap.put("id_necalli_vskill_description", R.string.necalli_vskill_description);
        stringIdMap.put("id_necalli_opening_dagger_description", R.string.necalli_opening_dagger_description);

        //Cammy Movelist
        stringIdMap.put("id_during_hooligan_combination", R.string.cammy_during_hooligan);
        stringIdMap.put("id_cammy_vtrigger_description", R.string.cammy_vtrigger_description);
        stringIdMap.put("id_cammy_vskill_description", R.string.cammy_vskill_description);

        //Dhalsim Movelist
        stringIdMap.put("id_dhalsim_critical_art_posttext", R.string.dhalsim_critical_art_posttext);
        stringIdMap.put("id_dhalsim_vtrigger_description", R.string.dhalsim_vtrigger_description);
        stringIdMap.put("id_dhalsim_vskill_description", R.string.dhalsim_vskill_description);

    }

    public static int getStringId(String key) {
        if (stringIdMap.containsKey(key)) {
            return stringIdMap.get(key);
        } else {
            throw new RuntimeException("could not find key: " + key);
        }
    }
}
