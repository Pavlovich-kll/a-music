import { Link } from 'react-router-dom';
import React from 'react';

export const columns = [
  {
    title: 'Title',
    dataIndex: 'title',
    key: 'title',
    render: (text: any, item: any) => <Link to={`/collection/${item._id}`}>{text}</Link>,
    width: 300,
    ellipsis: true,
  },
  {
    title: 'Description',
    dataIndex: 'description',
    key: 'description',
    ellipsis: true,
  },
  { title: 'Tracks count', dataIndex: 'track_count', key: 'track_count', width: 130 },
];
