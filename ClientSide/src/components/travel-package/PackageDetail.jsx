import { useParams } from "react-router-dom";

const PackageDetail = () => {
  const { id } = useParams();

  return (
    <div>
      <h1>Package Details Component</h1>
      <h2>package id: {id}</h2>
    </div>
  );
};

export default PackageDetail;
