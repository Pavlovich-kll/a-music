import { connect } from '../../helpers/connect';
import { IStore } from '../../store';
import { bindActionCreators } from 'redux';
import { TrackList } from './TrackList';
import { setToPlay, setIsPlay } from '../../actions/content';

export default (connect(
  ({ content }: IStore) => ({
    currentPlayList: content.currentPlayList,
    currentTrack: content.current,
  }),
  (dispatch) =>
    bindActionCreators(
      {
        setToPlay,
        setIsPlay,
      },
      dispatch
    )
) as any)(TrackList);
