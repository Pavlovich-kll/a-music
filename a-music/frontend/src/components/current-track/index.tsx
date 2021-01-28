import React from 'react';
import { pathOr } from 'ramda';
import { connect } from '../../helpers/connect';
import TrackCover from '../../ui/track-cover';
import { IStore } from '../../store';
import { ITrack } from '../../reducers/reducers.d';

const currentTrackSelector = (state: IStore): ITrack => pathOr({}, ['content', 'current'], state) as ITrack;

@(connect((state: IStore) => ({
  track: currentTrackSelector(state),
})) as any)
export default class CurrentTrack extends React.Component<any> {
  render() {
    const { track } = this.props;
    return <TrackCover {...track} />;
  }
}
