import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import CollectionView from './style';
import { CollectionHeader } from './collection-head';
import { CollectionTable } from './collection-table';
import { useSelector, useDispatch } from 'react-redux';
import { IStore } from '../../store';
import { ITrack } from '../../reducers/reducers';
import { deleteTrackFromPlaylist } from '../../actions/content';

type IdType = {
  id: string | number;
};

const Collection = (props: any) => {
  const { id } = useParams() as IdType;
  const playlists = useSelector(({ content }: IStore) => content.playLists);
  const [data, setData] = useState(null);
  const dispatch = useDispatch();

  useEffect(() => {
    if (playlists.length) {
      setData(playlists.find((item) => item._id === id));
    }
  }, [playlists]);

  const handleRemove = (trackId: number) => {
    const newPlaylist = playlists.map((item) => {
      if (item._id === id) {
        item.tracks = data.tracks.filter((item: any) => item.track_id !== trackId);
      }
      return item;
    });
    dispatch(deleteTrackFromPlaylist(newPlaylist));
  };

  if (!data) {
    return (
      <div>
        <p>Loading...</p>
      </div>
    );
  }

  return (
    <CollectionView>
      <CollectionHeader
        likes={data.likes}
        title={data.title}
        description={data.description}
        totalLength={data.total_length}
        lastUpdate={data.updatedAt}
        trackCount={data.tracks.length}
        pic={data.pic}
        authors={data.tracks.map(({ author }: ITrack) => author)}
      />
      <CollectionTable data={data.tracks} handleRemove={handleRemove} />
    </CollectionView>
  );
};

export default Collection;
