import React, { useState } from 'react';
import { TableItem } from './table-item';
import { useDispatch, useSelector } from 'react-redux';
import { addToPlaylist, clearPlaylist, setIsPlay } from '../../actions/content';
import { ITrack } from '../../reducers/reducers';
import SearchPanel from '../../components/search-panel/index';
import { setToPlay } from '../../actions/content';
import { IStore } from '../../store';

interface ICollectionTable {
  data: ITrack[];
  handleRemove(id: string | number): void;
}

export const CollectionTable = ({ data, handleRemove }: ICollectionTable) => {
  const dispatch = useDispatch();

  const handlePlayAll = () => {
    dispatch(clearPlaylist());
    dispatch(addToPlaylist(data, true));
    dispatch(setIsPlay(true));
  };

  const [searchPanel, setSearchPanel] = useState(false);
  const toggleSearchPanel = () => setSearchPanel(!searchPanel);

  const {
    content: { isPlay, current },
  } = useSelector(({ content }: IStore) => ({ content }));

  return (
    <div className="collection__body">
      <div className="collection__body-toolbar">
        <div className="collection__body-toolbar-left">
          <a onClick={handlePlayAll} className="collection__body-toolbar-left-play-all">
            Play all
          </a>
        </div>
        <div className="collection__body-toolbar-right">
          <a className="collection__body-toolbar-left-search" onClick={toggleSearchPanel}>
            {!searchPanel ? 'Search on the playlist' : 'Hide panel'}
          </a>
          {!!searchPanel && (
            <div className="collection__body-searchpanel">
              <SearchPanel currentData={data} setToPlay={setToPlay} addTrackInPlayList={null} setNullSearch={null} />
            </div>
          )}
        </div>
      </div>
      <table className="collection__body-table">
        <thead>
          <tr>
            <th>#</th>
            <th />
            <th>TRACK</th>
            <th>ARTIST</th>
            <th>ALBUM</th>
            <th>GENRE</th>
            <th>LIKES</th>
            <th>PLAY</th>
            <th>EDIT</th>
          </tr>
        </thead>
        <tbody>
          {data.map((track: ITrack, id) => (
            <TableItem
              track={track}
              key={track.track_id}
              track_number={id + 1}
              isPlaying={current?.track_id === track.track_id && isPlay}
              handleRemove={handleRemove}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};
