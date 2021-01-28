import React, {useState} from 'react'
import {useDispatch, useSelector} from "react-redux";
import {List} from 'antd';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';
import {TitleSmallTypography} from '../src/components/send-music/style'
import {SendButton, CloseButton, AddButton, PlaylistSpan, LinkStyled} from '../src/components/dashboard/styled';
import SearchPanel from "../src/components/search-panel";
import {
  clearTracksInPlayList,
  deleteTrackInPlayList,
  setFlagEditPlayListLink,
} from "../src/actions/content";
import {updateTrackInPlayList} from "../src/actions/content-async"


interface ITablePlayListEditProps {
  setToPlay: Function;
  addTrackInPlayList: Function;
  isVisibleSearchPanel: boolean;
  playlistDataObj: any;
  handleSetId: any;
}

const TablePlayListEdit = ({
                             setToPlay,
                             addTrackInPlayList,
                             isVisibleSearchPanel,
                             playlistDataObj,
                             handleSetId,
                           }: ITablePlayListEditProps) => {
  const dispatch = useDispatch();
  const [visible, setVisible] = useState<boolean>(isVisibleSearchPanel);
  const [nullSearch, setNullSearch] = React.useState();
  const createPlayListTracks = useSelector(({content}: any) => content.createPlayListTracks);

  const handleSetVisible = (isVisible: boolean) => (): void => {
    setVisible(isVisible);
    dispatch(clearTracksInPlayList());
  };

  const handleUpdateTracks = (playlist: any, tracks: any) => (): void => {
    dispatch(updateTrackInPlayList(playlist, tracks));
    dispatch(clearTracksInPlayList());
  };
  
  return (
    <>
      <div className={'container-div-playlist-edit'}>
        <AddButton className={'button-back'} onClick={handleSetId('')}><ArrowBackIcon/></AddButton>
        <PlaylistSpan className={'title-description-tracks-tests'}>Title: {playlistDataObj.title}</PlaylistSpan>
        <PlaylistSpan
          className={'title-description-tracks-tests'}>Description: {playlistDataObj.description}</PlaylistSpan>
        <PlaylistSpan className={'title-description-tracks-tests'}>Tracks:</PlaylistSpan>
        <List
          size="small"
          bordered
          dataSource={playlistDataObj.tracks}
          renderItem={(item: any) => (
            <List.Item
              actions={
                [<a key="list-loadmore-edit"
                  //TODO: сделать onClick на удаление треков из плейлистов, когда это будет реализованно.
                >
                  delete
                </a>]
              }
            >
              <List.Item.Meta
                title={item.title}
              />
            </List.Item>
          )}
        />
        <AddButton className={'button-add-tracks-test'} onClick={handleSetVisible(true)}> Add
          tracks </AddButton>
        {visible &&
        <div className={'search-playlist-track'}>
          <PlaylistSpan className={'find-your-track-test'}>Find your track:</PlaylistSpan>
          <SearchPanel
            setToPlay={setToPlay}
            isEditPlayList
            playlistDataObj={playlistDataObj}
            createPlayListTracks={createPlayListTracks}
            addTrackInPlayList={addTrackInPlayList}
            setNullSearch={setNullSearch}
          />
          {createPlayListTracks &&
          <List
            size="small"
            bordered
            dataSource={createPlayListTracks}
            renderItem={(item: any) => (
              <List.Item
                actions={
                  [<a key="list-loadmore-edit"
                      onClick={() => dispatch(deleteTrackInPlayList(item.track_id))}
                  >
                    delete
                  </a>]
                }
              >
                <List.Item.Meta
                  title={<div>{item.label}</div>}
                />
              </List.Item>
            )}
          />
          }
          {nullSearch &&
          <div>
            <TitleSmallTypography>
              Sorry, we don't have this track
            </TitleSmallTypography>
            <LinkStyled to={'/send-music'}>
              <span onClick={() => dispatch(setFlagEditPlayListLink(true))}>Add track</span>
            </LinkStyled>
          </div>
          }
          <SendButton
            onClick={handleUpdateTracks(playlistDataObj, createPlayListTracks)}
            className={'send-buttons-test'}
            disabled={!createPlayListTracks}
          >Send</SendButton>
          <CloseButton
            className={'close-buttons-test'}
            onClick={handleSetVisible(false)}
          >Close</CloseButton>
        </div>
        }
      </div>
    </>
  )
}
export default TablePlayListEdit;
