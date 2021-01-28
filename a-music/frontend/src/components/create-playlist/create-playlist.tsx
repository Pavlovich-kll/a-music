import React from 'react';
import { head } from 'ramda';
import { TextField, Button, Typography } from '@material-ui/core';
import { List } from 'antd';
import { WrappedForm, HideButton, TitleTypography } from '../send-music/style';
import { Formik, FormikProps } from 'formik';
import { SendPlayListFormSchema } from '../../validation-schemas';
import { DASHBOARD_PATH } from '../../router';
import { Link, useHistory } from 'react-router-dom';
import SearchPanel from '../search-panel/index';
import { ITrack } from '../../reducers/reducers';

interface ISendPlayListProps {
  setToPlay: Function;
  addTrackInPlayList: Function;
  createPlayListTracks: ITrack[];
  deleteTrackInPlayList: Function;
  uploadPlayList: Function;
  clearTracksInPlayList: Function;
}

export interface IFormCreatePlaylist {
  title: string;
  description: string;
}

const InitialValues: IFormCreatePlaylist = {
  title: '',
  description: '',
};

const CreatePlaylist = ({
  setToPlay,
  addTrackInPlayList,
  createPlayListTracks,
  deleteTrackInPlayList,
  uploadPlayList,
  clearTracksInPlayList,
}: ISendPlayListProps) => {
  const picture = React.useRef(null);
  const [pic, setPic] = React.useState();
  const { push } = useHistory();

  const handleSubmit = async (values: IFormCreatePlaylist): Promise<void> => {
    await uploadPlayList({
      pic: head(picture.current.files),
      ...values,
      tracks: createPlayListTracks.map((item: ITrack) => item._id),
    });
    clearTracksInPlayList();
    push(DASHBOARD_PATH);
  };

  return (
    <Formik initialValues={InitialValues} onSubmit={handleSubmit} validationSchema={SendPlayListFormSchema}>
      {({ errors, values, handleChange, isSubmitting }: FormikProps<IFormCreatePlaylist>) => (
        <WrappedForm>
          <Typography variant="h1">Create Playlist</Typography>

          <HideButton
            // @ts-ignore: Unreachable code error
            onClick={clearTracksInPlayList}
          >
            <Link to={'/dashboard'}>🡰</Link>
          </HideButton>
          <TextField
            value={values.title}
            fullWidth
            name="title"
            id="title"
            label="Title"
            onChange={handleChange}
            variant="outlined"
            placeholder="Enter title"
            data-testid="input-title"
          />
          {errors.title && <div className="error-field">{errors.title}</div>}
          <TextField
            value={values.description}
            fullWidth
            name="description"
            label="Description"
            onChange={handleChange}
            variant="outlined"
            placeholder="Enter description"
            data-testid="input-description"
          />
          {errors.description && <div className="error-field">{errors.description}</div>}
          <div>
            <input
              accept="image/*"
              className="input"
              id="contained-button-file"
              multiple
              type="file"
              ref={picture}
              onChange={(e: any) => {
                setPic(e.target.value);
              }}
            />
            <label className="picture" htmlFor="contained-button-file">
              <Button className="upload" variant="contained" color="primary" component="span">
                Upload Cover
              </Button>
            </label>
            <div className="status-input">{pic ? pic.replace(/^.*[\\]/, '') : 'No file selected'}</div>
          </div>
          <div>
            <TitleTypography>Find music</TitleTypography>
            <SearchPanel
              setToPlay={setToPlay}
              createPlayListTracks={createPlayListTracks}
              isPlayList
              addTrackInPlayList={addTrackInPlayList}
            />
            {createPlayListTracks && createPlayListTracks.length > 0 && (
              <>
                <TitleTypography>Tracks:</TitleTypography>
                <div>
                  <List
                    size="small"
                    bordered
                    dataSource={createPlayListTracks}
                    renderItem={(item: any) => (
                      <List.Item
                        actions={[
                          <a key="list-loadmore-edit" onClick={() => deleteTrackInPlayList(item.track_id)}>
                            delete
                          </a>,
                        ]}
                      >
                        <List.Item.Meta title={<div>{item.label}</div>} />
                      </List.Item>
                    )}
                  />
                </div>
              </>
            )}
          </div>
          <Button
            data-testid="submit-btn"
            id="submit"
            fullWidth
            type="submit"
            variant="outlined"
            color="primary"
            className="send"
            disabled={isSubmitting}
          >
            Send
          </Button>
        </WrappedForm>
      )}
    </Formik>
  );
};
export default CreatePlaylist;
