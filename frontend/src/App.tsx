import React from 'react';
import './App.css';
import { Input, Space } from 'antd';
import 'antd/dist/antd.css';

const { Search } = Input;

function App() {
  const [shortUrl, setShortUrl] = React.useState("");
  const [error, setError] = React.useState("");

  async function onSearch(originalUrl: any) {
    // TODO: add originalUrl validation
    setError("");
    setShortUrl("");
    const params = new URLSearchParams({originalUrl})
      const resp = await fetch(`api/links/create?${params}`, {
      method: "POST"
    })
    if (resp.ok) {
      const json = await resp.json();
      setShortUrl(json.shortUrl);
      console.log("shortUrl", shortUrl)
    } else {
      setError('Something went wrong. Please try again later');
    }
  }
  return (
    <div id="box">
      <h1>
        UrlShortener
      </h1>
      <Space size="large" direction="vertical">
        <Search
            style={{width: '40em'}}
            pattern="https?://.*"
            type="url"
            placeholder="https://example.com"
            allowClear
            onSearch={onSearch}
            enterButton="ShortenMe"
        />
      </Space>
      <div id="drop">
        {shortUrl && (
            <div>
            Your short link: <a id="shortUrl" href={shortUrl}>{shortUrl}</a>
            </div>
        )}
      </div>
      {error && (
        <div>{error}</div>
      )}
    </div>
  );
}

export default App;
