import React from 'react';
import ActualView from './actual-playlist-style';
import { MaximizeSVG, CloseSVG } from './svg/actual-playlist-svg';

interface IActualPlauListProps {
  content: Function | React.ReactNode;
  toClose: (e: any) => void;
}

const ActualPlaylist = ({ content, toClose }: IActualPlauListProps) => {
  return (
    <ActualView>
      <div className="block-header">
        <span className="span-title">Play Queue</span>
        <MaximizeSVG />
        <span onClick={toClose}>
          <CloseSVG />
        </span>
      </div>
      <div className="aside-content">{typeof content === 'function' ? content() : content}</div>
    </ActualView>
  );
};

export default ActualPlaylist;
