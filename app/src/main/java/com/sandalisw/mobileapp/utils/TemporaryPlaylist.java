package com.sandalisw.mobileapp.utils;

import com.sandalisw.mobileapp.models.Song;

import java.util.ArrayList;
import java.util.List;


public class TemporaryPlaylist {
    List<Song> mdata = new ArrayList<>();
    List<Song> mData = new ArrayList<>();

    private void setData(){
        mData.add(new Song(23,
                "Chain",
                "Cairo Rich ft. Costa",
                "https://i.ytimg.com/vi/JmgQ6lY2G1A/maxresdefault.jpg",
                "https://helabeat.s3-ap-southeast-1.amazonaws.com/songs/cairo_rich_chain_audio_ft_costa_wild_skatey.mp3"

        ));

    }

    public List<Song> getData(){
        setData();
        return mData;
    }


    private void setdata(){
        mdata.add(new Song(60,
                "I can't Keep Lying",
                "Iraj Weeraratne",
                "https://i1.sndcdn.com/artworks-000305925720-xtlwtj-t500x500.jpg",
                "https://helabeat.s3-ap-southeast-1.amazonaws.com/songs/iraj_i_cant_keep_lying.mp3"

        ));
        mdata.add(
                new Song( 27,
                        "Cuore Nero (Italian)",
                        "Cairo Rich ",
                        "https://i.ytimg.com/vi/JmgQ6lY2G1A/maxresdefault.jpg",
                        "https://helabeat.s3-ap-southeast-1.amazonaws.com/songs/cairo_rich_cuore_nero_audio_italian_.mp3")
        );
        mdata.add(new Song(
                67,
                "Kalakanni loke",
                "Neo ft. Freaky MobBig",
                "https://sarigama.lk/img/default/song.png",
                "https://helabeat.s3-ap-southeast-1.amazonaws.com/songs/kalakanni_loke_neo_feat_freaky_mo.mp3"
        ));
        mdata.add(new Song(
                68,
                "Kalu Hitha",
                "Cairo Rich",
                "https://i.ytimg.com/vi/JmgQ6lY2G1A/maxresdefault.jpg",
                "https://helabeat.s3-ap-southeast-1.amazonaws.com/songs/cairo_rich_kalu_hitha.mp3"
        ));
        mdata.add(new Song(
                68,
                "Kalu Hitha",
                "Cairo Rich",
                "https://i.ytimg.com/vi/JmgQ6lY2G1A/maxresdefault.jpg",
                "https://helabeat.s3-ap-southeast-1.amazonaws.com/songs/cairo_rich_kalu_hitha.mp3"
        ));
        mdata.add(new Song(
                97,
                "Maaraya[2018]",
                "IRAj fr. Big Doggy, Chey Nyn, Master D & Spin",
                "https://sarigama.lk/img/default/song.png",
                "https://helabeat.s3-ap-southeast-1.amazonaws.com/songs/iraj_maaraya_ft_big_doggy_chey_nyn_.mp3"
        ));
        mdata.add(new Song(
                129,
                "Oba Dakala",
                "YUKI",
                "https://decibel.lk/assets/2019/04/yuki-1.jpg",
                "https://helabeat.s3-ap-southeast-1.amazonaws.com/songs/yuki_oba_dakala_.mp3"
        ));
        mdata.add(new Song(
                141,
                "Pawena Loke",
                "Cairo Rich ft. Aj Leon",
                "https://i.ytimg.com/vi/JmgQ6lY2G1A/maxresdefault.jpg",
                "https://helabeat.s3-ap-southeast-1.amazonaws.com/songs/Cairo+Rich+-+Pawenne+loke+(Audio)+ft.+Aj+leon.mp3"
        ));



    }



    public List<Song> getdata(){
        setdata();
        return  mdata;
    }

}

