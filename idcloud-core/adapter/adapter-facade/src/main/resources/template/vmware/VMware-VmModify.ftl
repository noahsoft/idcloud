{
"providerUrl":"${providerUrl}",
"authUser":"${authUser}",
"authPass":"${authPass}",
"vmName":"${vmName}",
"cpu":"${cpu}",
"memory":"${memory}",
"osCategory":"${osCategory}",
"osCategoryDetail":"${osCategoryDetail}",
"disks":
[<#list disks as disk>
{"name":"${disk.name}",
"size":"${disk.size}",
"location":"${disk.location}",
"operate":"${disk.operate}",
"id":"${disk.id}",
"path":"${disk.path!myDefault}"},
</#list>],
"nics":
[<#list nics as nic>
{"publicIp":"${nic.publicIp}",
"privateIp":"${nic.privateIp}",
"dns":"${nic.dns}",
"netmask":"${nic.netmask}",
"gateway":"${nic.gateway}",
"port":"${nic.port}",
"operate":"${nic.operate}",
"primary":"${nic.primary?c}",
"mac":"${nic.mac}"},
</#list>]
}