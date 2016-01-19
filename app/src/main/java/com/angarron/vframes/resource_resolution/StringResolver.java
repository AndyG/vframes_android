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
        stringIdMap.put("id_hits_low", R.string.hits_low);
        stringIdMap.put("id_command_grab", R.string.command_grab);

        //Ryu Movelist
        stringIdMap.put("id_ryu_hadoken_description", R.string.ryu_hadoken_description);
        stringIdMap.put("id_ryu_shoryuken_description", R.string.ryu_shoryuken_description);
        stringIdMap.put("id_ryu_tatsu_description", R.string.ryu_tatsu_description);
        stringIdMap.put("id_ryu_vtrigger_description", R.string.ryu_vtrigger_description);
        stringIdMap.put("id_ryu_vskill_description", R.string.ryu_vskill_description);

        //Karin Movelist
        stringIdMap.put("id_karin_tenko_posttext", R.string.karin_tenko_posttext);
        stringIdMap.put("id_karin_meioken_posttext", R.string.karin_meioken_posttext);
        stringIdMap.put("id_karin_kanzuki_ryu_hokojutsu_sappo_description", R.string.karin_kanzuki_ryu_hokojutsu_sappo_description);
        stringIdMap.put("id_karin_tenko_description", R.string.karin_tenko_description);
        stringIdMap.put("id_karin_orochi_description", R.string.karin_orochi_description);
        stringIdMap.put("id_karin_senha_resshu_description", R.string.karin_senha_resshu_description);
        stringIdMap.put("id_karin_vskill_description", R.string.karin_vskill_description);
        stringIdMap.put("id_karin_vtrigger_description", R.string.karin_vtrigger_description);
        stringIdMap.put("id_karin_mujinkyaku_description", R.string.karin_mujinkyaku_description);

        //Claw Movelist
        stringIdMap.put("id_claw_claw_on_only", R.string.claw_claw_on_only);
        stringIdMap.put("id_claw_claw_off_only", R.string.claw_claw_off_only);
        stringIdMap.put("id_claw_switch", R.string.claw_switch);
        stringIdMap.put("id_claw_flying_barcelona_attack_description", R.string.claw_flying_barcelona_attack_description);
        stringIdMap.put("id_claw_vskill_description", R.string.claw_vskill_description);
        stringIdMap.put("id_claw_vtrigger_torero_description", R.string.claw_vtrigger_torero_description);
        stringIdMap.put("id_claw_vtrigger_rojo_description", R.string.claw_vtrigger_rojo_description);
        stringIdMap.put("id_claw_vtrigger_azul_description", R.string.claw_vtrigger_azul_description);
        stringIdMap.put("id_claw_during_flying_barcelona_attack", R.string.claw_during_flying_barcelona_attack);

        //Chun Movelist
        stringIdMap.put("id_up_to_three_times", R.string.chun_up_to_three_times);
        stringIdMap.put("id_chun_vtrigger_description", R.string.chun_vtrigger_description);
        stringIdMap.put("id_chun_vskill_description", R.string.chun_vskill_description);
        stringIdMap.put("id_chun_senenshu_description", R.string.chun_senenshu_description);
        stringIdMap.put("id_chun_kikoken_description", R.string.chun_kikoken_description);
        stringIdMap.put("id_chun_airborne_lightning_legs_description", R.string.chun_airborne_lightning_legs_description);
        stringIdMap.put("id_chun_spinning_bird_kick_description", R.string.chun_spinning_bird_kick_description);
        stringIdMap.put("id_chun_critical_art_description", R.string.chun_critical_art_description);

        //Ken Movelist
        stringIdMap.put("id_ken_hadoken_description", R.string.ken_hadoken_description);
        stringIdMap.put("id_ken_shoryuken_description", R.string.ken_shoryuken_description);
        stringIdMap.put("id_ken_tatsu_description", R.string.ken_tatsu_description);
        stringIdMap.put("id_ken_airborne_tatsu_description", R.string.ken_airborne_tatsu_description);
        stringIdMap.put("id_ken_vtrigger_description", R.string.ken_vtrigger_description);
        stringIdMap.put("id_ken_vskill_description", R.string.ken_vskill_description);
        stringIdMap.put("id_ken_thunder_kick_posttext", R.string.ken_thunder_kick_posttext);
        stringIdMap.put("id_ken_thunder_kick_description", R.string.hits_overhead);

        //Zangief Movelist
        stringIdMap.put("id_hold_buttons_for_special_lariat", R.string.zangief_hold_buttons_for_special_lariat);
        stringIdMap.put("id_can_move_forward_while_holding_buttons", R.string.zangief_can_move_forward_while_holding_buttons);
        stringIdMap.put("id_zangief_siberian_express_description", R.string.zangief_siberian_express_description);
        stringIdMap.put("id_zangief_vtrigger_description", R.string.zangief_vtrigger_description);
        stringIdMap.put("id_zangief_vskill_description", R.string.zangief_vskill_description);

        //Dictator Movelist
        stringIdMap.put("id_after_head_press", R.string.dictator_after_head_press);
        stringIdMap.put("id_before_head_press", R.string.dictator_before_head_press);
        stringIdMap.put("id_psycho_reflect_hold_buttons", R.string.dictator_psycho_reflect_hold_buttons);
        stringIdMap.put("id_dictator_psycho_blast_description", R.string.dictator_psycho_blast_description);
        stringIdMap.put("id_devil_reverse_description", R.string.dictator_devil_reverse_description);
        stringIdMap.put("id_dictator_vskill_description", R.string.dictator_vskill_description);
        stringIdMap.put("id_dictator_vtrigger_description", R.string.dictator_vtrigger_description);

        //Laura Movelist
        stringIdMap.put("id_laura_hold_buttons_to_power_up", R.string.laura_hold_buttons_to_power_up);
        stringIdMap.put("id_laura_after_mp_or_ex_bolt_charge", R.string.laura_after_mp_or_ex_bolt_charge);
        stringIdMap.put("id_laura_bolt_charge_description", R.string.laura_bolt_charge_description);
        stringIdMap.put("id_laura_split_river_description", R.string.laura_split_river_description);
        stringIdMap.put("id_laura_rodeo_break_description", R.string.laura_rodeo_break_description);
        stringIdMap.put("id_laura_thunder_clap_description", R.string.laura_thunder_clap_description);
        stringIdMap.put("id_laura_vtrigger_description", R.string.laura_vtrigger_description);
        stringIdMap.put("id_laura_vskill_overhead_description", R.string.laura_vskill_overhead_description);
        stringIdMap.put("id_laura_vskill_avante_description", R.string.vskill_avante_description);
        stringIdMap.put("id_laura_vskill_esquiva_description", R.string.vskill_esquiva_description);

        //Birdie Movelist
        stringIdMap.put("id_birdie_hanging_chain_posttext", R.string.birdie_hanging_chain_posttext);
        stringIdMap.put("id_birdie_bull_head_description", R.string.birdie_bull_head_description);
        stringIdMap.put("id_birdie_bull_horn_description", R.string.birdie_bull_horn_description);
        stringIdMap.put("id_birdie_hanging_chain_description", R.string.birdie_hanging_chain_description);
        stringIdMap.put("id_birdie_killing_head_description", R.string.birdie_killing_head_description);
        stringIdMap.put("id_birdie_bull_revenger_description", R.string.birdie_bull_revenger_description);
        stringIdMap.put("id_birdie_vtrigger_description", R.string.birdie_vtrigger_description);

        //Mika Movelist
        stringIdMap.put("id_mika_shooting_peach_description", R.string.mika_shooting_peach_description);
        stringIdMap.put("id_mika_wingless_airplane_description", R.string.mika_wingless_airplane_description);
        stringIdMap.put("id_mika_typhoon_description", R.string.mika_typhoon_description);
        stringIdMap.put("id_mika_brimstone_description", R.string.mika_brimstone_description);
        stringIdMap.put("id_mika_vtrigger_posttext", R.string.mika_vtrigger_posttext);
        stringIdMap.put("id_mika_vskill_posttext", R.string.mika_vskill_posttext);
        stringIdMap.put("id_mika_vskill_description", R.string.mika_vskill_description);
        stringIdMap.put("id_mika_passion_rope_throw_pretext", R.string.mika_passion_rope_throw_pretext);
        stringIdMap.put("id_mika_passion_rope_throw_posttext", R.string.mika_passion_rope_throw_posttext);

        //Rashid Movelist
        stringIdMap.put("id_rashid_repeat_buttons_to_extend", R.string.rashid_repeat_buttons_to_extend);
        stringIdMap.put("id_rashid_vtrigger_description", R.string.rashid_vtrigger_description);
        stringIdMap.put("id_rashid_vskill_divekick_pretext", R.string.rashid_vskill_divekick_pretext);
        stringIdMap.put("id_rashid_vskill_rollkick_pretext", R.string.rashid_vskill_rollkick_pretext);
        stringIdMap.put("id_rashid_spinning_mixer_description", R.string.rashid_spinning_mixer_description);
        stringIdMap.put("id_rashid_whirlwind_shot_description", R.string.rashid_whirlwind_shot_description);

        //Rashid Frame Data
        stringIdMap.put("id_rashid_eagle_spike_on_block_description", R.string.rashid_eagle_spike_on_block_description);

        //Nash Movelist
        stringIdMap.put("id_nash_press_for_second_boom", R.string.nash_press_for_second_boom);
        stringIdMap.put("id_nash_sonic_boom_description", R.string.nash_sonic_boom_description);
        stringIdMap.put("id_nash_sonic_scythe_description", R.string.nash_sonic_scythe_description);
        stringIdMap.put("id_nash_tragedy_assault_description", R.string.nash_tragedy_assault_description);
        stringIdMap.put("id_nash_sonic_move_hide_description", R.string.nash_sonic_move_hide_description);
        stringIdMap.put("id_nash_sonic_move_blitz_air_description", R.string.nash_sonic_move_blitz_air_description);
        stringIdMap.put("id_nash_sonic_move_steel_air_description", R.string.nash_sonic_move_steel_air_description);
        stringIdMap.put("id_nash_critical_art_description", R.string.nash_critical_art_description);
        stringIdMap.put("id_nash_vskill_description", R.string.nash_vskill_description);

        //Necalli Movelist
        stringIdMap.put("id_necalli_vskill_control_distance", R.string.necalli_vskill_control_distance);
        stringIdMap.put("id_necalli_disc_guidance_description", R.string.necalli_disc_guidance_description);
        stringIdMap.put("id_necalli_raging_light_description", R.string.necalli_raging_light_description);
        stringIdMap.put("id_necalli_mask_of_tlalli_description", R.string.necalli_mask_of_tlalli_description);
        stringIdMap.put("id_necalli_vtrigger_description", R.string.necalli_vtrigger_description);
        stringIdMap.put("id_necalli_vskill_description", R.string.necalli_vskill_description);
        stringIdMap.put("id_necalli_opening_dagger_description", R.string.necalli_opening_dagger_description);

        //Cammy Movelist
        stringIdMap.put("id_during_hooligan_combination", R.string.cammy_during_hooligan);
        stringIdMap.put("id_cammy_spiral_arrow_description", R.string.cammy_spiral_arrow_description);
        stringIdMap.put("id_cammy_cannon_spike_description", R.string.cammy_cannon_spike_description);
        stringIdMap.put("id_cammy_cannon_strike_description", R.string.cammy_cannon_strike_description);
        stringIdMap.put("id_cammy_hooligan_combination_description", R.string.cammy_hooligan_combination_description);
        stringIdMap.put("id_cammy_vtrigger_description", R.string.cammy_vtrigger_description);
        stringIdMap.put("id_cammy_vskill_description", R.string.cammy_vskill_description);
        stringIdMap.put("id_close_to_grounded_opponent_during_hooligan", R.string.hooligan_close_to_grounded_opponent);
        stringIdMap.put("id_close_to_airborne_opponent_during_hooligan", R.string.hooligan_close_to_airborne_opponent);

        //Dhalsim Movelist
        stringIdMap.put("id_dhalsim_critical_art_posttext", R.string.dhalsim_critical_art_posttext);
        stringIdMap.put("id_dhalsim_yoga_fire_description", R.string.dhalsim_yoga_fire_description);
        stringIdMap.put("id_dhalsim_yoga_flame_description", R.string.dhalsim_yoga_flame_description);
        stringIdMap.put("id_dhalsim_yoga_teleport_description", R.string.dhalsim_yoga_teleport_description);
        stringIdMap.put("id_dhalsim_vtrigger_description", R.string.dhalsim_vtrigger_description);
        stringIdMap.put("id_dhalsim_vskill_description", R.string.dhalsim_vskill_description);

        //Fang Movelist
        stringIdMap.put("id_fang_vskill_description", R.string.fang_vskill_description);
        stringIdMap.put("id_fang_vtrigger_description", R.string.fang_vtrigger_description);
        stringIdMap.put("id_fang_critical_art_description", R.string.fang_critical_art_description);

    }

    public static int getStringId(String key) {
        if (stringIdMap.containsKey(key)) {
            return stringIdMap.get(key);
        } else {
            throw new RuntimeException("could not find key: " + key);
        }
    }
}
