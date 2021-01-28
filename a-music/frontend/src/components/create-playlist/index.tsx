import { bindActionCreators } from 'redux';
import { connect } from '../../helpers/connect';
import { addTrackInPlayList, setToPlay, deleteTrackInPlayList, clearTracksInPlayList } from '../../actions/content';
import { uploadPlayList, uploadMusic } from '../../actions/content-async';

import CreatePlaylist from './create-playlist';
import { IStore } from '../../store';

export default connect(
  ({ content }: IStore) => ({
    createPlayListTracks: content.createPlayListTracks,
  }),
  (dispatch: any) =>
    bindActionCreators(
      {
        uploadMusic,
        setToPlay,
        deleteTrackInPlayList,
        uploadPlayList,
        clearTracksInPlayList,
        addTrackInPlayList,
      },
      dispatch
    )
)(CreatePlaylist);
