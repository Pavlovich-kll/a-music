import React, { useState } from 'react';
import Playlist from './playlist';
import { IPlayList } from '../../../../reducers/reducers';

interface IDashBoardPlaylists {
  playLists: IPlayList[];
}

const PlayLists = ({ playLists }: IDashBoardPlaylists) => {
  const [currentPlayListId, setIsPlaying] = useState<string>('');
  return (
    <>
      {playLists.map(({ _id, pic, title, tracks }: IPlayList) => (
        <Playlist
          key={_id}
          _id={_id}
          pic={pic}
          title={title}
          track_count={tracks.length}
          tracks={tracks}
          isPlaying={currentPlayListId === _id}
          setIsPlaying={setIsPlaying}
        />
      ))}
    </>
  );
};
export default PlayLists;
